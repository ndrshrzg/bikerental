package org.ndrshrzg.bikerental.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ndrshrzg.bikerental.db.BikeRepository;
import org.ndrshrzg.bikerental.db.SessionRepository;
import org.ndrshrzg.bikerental.db.UserRepository;
import org.ndrshrzg.bikerental.ex.*;
import org.ndrshrzg.bikerental.model.Bike;
import org.ndrshrzg.bikerental.model.Session;
import org.ndrshrzg.bikerental.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring RestController class that implements methods exposed at the specified paths.
 * Instantiates JpaRepository classes to access persistence layer.
 */
@RestController
public class BikeController {

    private final BikeRepository bikeRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(BikeController.class);
    private static final ObjectMapper om = new ObjectMapper();

    /**
     * Constructor to instantiate JpaRepository Beans.
     *
     * @param bikeRepository JpaRepository handling Bikes
     * @param sessionRepository JpaRepository handling Bikes
     * @param userRepository JpaRepository handling Bikes
     */
    BikeController(BikeRepository bikeRepository, SessionRepository sessionRepository, UserRepository userRepository) {
        this.bikeRepository = bikeRepository;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get all Bikes from database.
     *
     * @return List of Bike as JSON array sent to client.
     * @throws JsonProcessingException error mapping database object to JSON
     */
    @GetMapping(value = "/bikes", produces = MediaType.APPLICATION_JSON_VALUE)
    String getBikes() throws JsonProcessingException {
        return om.writeValueAsString(bikeRepository.findAll());
    }

    /**
     * Get Bike data by bikeId.
     *
     * @param bikeId Id of the Bike
     * @return Bike as JSON object sent to  client.
     * @throws JsonProcessingException error mapping database object to JSON
     */
    @GetMapping(value = "/bikes/{bikeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    String getBikeByBikeId(@PathVariable Long bikeId) throws JsonProcessingException {
        return om.writeValueAsString(bikeRepository.findById(bikeId).orElseThrow(() -> new BikeNotFoundException(bikeId)));
    }


    /**
     * Rent a bike by bikeId.
     *
     * @param bikeId  The id of the Bike to rent.
     * @param request HttpServletRequest context to identify User.
     * @return String message indicating success or error sent to the client.
     */
    @PostMapping(value = "/rent/{bikeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    String rentBikeByBikeId(@PathVariable Long bikeId, HttpServletRequest request) throws JsonProcessingException {
        // check if bike exists
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new BikeNotFoundException(bikeId));

        String sessionId = request.getSession().getId();
        String userName = request.getRemoteUser();
        User user = userRepository.findByUsername(userName);

        // User already has an active rental session
        if (userHasActiveSession(user.getId())) {
            throw new UserHasActiveSessionException(user.getId());
        }
        // User trying to rent a bike that is not free
        if (!bike.isFree()) {
            throw new BikeAlreadyRentedException(bikeId);
        } else {
            // instantiate session
            Long sessionStart = System.currentTimeMillis();
            Session newSession = new Session(sessionId, user.getId(), user.getUsername(), bike.getBikeId(), bike.getFrame(), sessionStart);
            // start new rental session
            startRentalSession(newSession);
            // return OK response
            return om.writeValueAsString(new RentResponse(newSession));
        }

    }

    /**
     * Return a Bike by bikeId.
     *
     * @param bikeId  The Id of the Bike to be returned.
     * @param request HttpServletRequest context to identify User.
     * @return String message indicating success or error sent to the client.
     */
    @PostMapping(value = "/return/{bikeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    String returnBikeByBikeId(@PathVariable("bikeId") Long bikeId, HttpServletRequest request) throws JsonProcessingException {
        // check if bike exists
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new BikeNotFoundException(bikeId));

        String userName = request.getRemoteUser();
        User user = userRepository.findByUsername(userName);

        // User has no active rental session
        if (!userHasActiveSession(user.getId())) {
            throw new UserHasNoActiveSessionException(user.getId());
        } else {
            // User trying to return a bike that is already free
            if (bike.isFree()) {
                throw new BikeNotRentedException(bikeId);
            } else {
                Session activeSession = sessionRepository.findByUserIdAndSessionEndIsNull(user.getId());
                Long sessionBikeId = activeSession.getBikeId();
                // User trying to return a bike they did not rent
                if (!sessionBikeId.equals(bikeId)) {
                    throw new BikeNotRentedByUserException(user.getId(), bikeId);
                } else {
                    Long timestamp = System.currentTimeMillis();
                    endRentalSession(activeSession, timestamp);
                    return om.writeValueAsString(new ReturnResponse(activeSession, timestamp));
                }
            }
        }
    }

    /**
     * Starts a new rental session.
     *
     * @param session the session to be started.
     */
    private void startRentalSession(Session session) {
        // save new session
        sessionRepository.save(session);
        // mark Bike as rented
        bikeRepository.setBikeIsFree(session.getBikeId(), false);
        // mark User to have rented
        userRepository.setUserHasRented(session.getUserId(), true);
    }

    /**
     * Ends a rental session.
     *
     * @param session the session to be ended.
     * @param timestamp current time in milliseconds since epoch.
     */
    private void endRentalSession(Session session, Long timestamp) {
        // add timestamp to session end
        Long sessionDurationSeconds = (timestamp - session.getSessionStart()) / 1000L;
        sessionRepository.setSessionEnd(session.getSessionId(), timestamp, sessionDurationSeconds);
        // mark bike as free
        bikeRepository.setBikeIsFree(session.getBikeId(), true);
        // mark User to have stopped renting
        userRepository.setUserHasRented(session.getUserId(), false);
    }

    /**
     * Check whether a User has an active session.
     *
     * @param userId Id of the requesting User.
     * @return true if the User has an active session
     */
    private boolean userHasActiveSession(Long userId) {

        return userRepository.getRentedById(userId);

    }


}
