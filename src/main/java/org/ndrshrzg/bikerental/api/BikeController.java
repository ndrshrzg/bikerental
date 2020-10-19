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
     * @param bikeRepository
     * @param sessionRepository
     * @param userRepository
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
     * @throws JsonProcessingException
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
     * @throws JsonProcessingException
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
    String rentBikeByBikeId(@PathVariable Long bikeId, HttpServletRequest request) {
        // check if bike exists
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new BikeNotFoundException(bikeId));

        String sessionId = request.getSession().getId();
        String userName = request.getRemoteUser();
        User user = userRepository.findByName(userName);

        // User already has an active rental session
        if (userHasActiveSession(user.getId())) {
            throw new UserHasActiveSessionException(user.getId());
        }
        // User trying to rent a bike that is not free
        if (!bike.isFree()) {
            throw new BikeAlreadyRentedException(bikeId);
        } else {
            // start new rental session
            startRentalSession(sessionId, user, bike);
            // return OK response
            return "{\"message\":\"OK\"}";
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
    String returnBikeByBikeId(@PathVariable("bikeId") Long bikeId, HttpServletRequest request) {
        // check if bike exists
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new BikeNotFoundException(bikeId));

        // User trying to return a bike that is already free
        if (bike.isFree()) {
            throw new BikeNotRentedException(bikeId);
        }

        String userName = request.getRemoteUser();
        User user = userRepository.findByName(userName);

        // User has no active rental session
        if (!userHasActiveSession(user.getId())) {
            throw new UserHasNoActiveSessionException(user.getId());
        } else {
            Session activeSession = sessionRepository.findByUserId(user.getId());
            Long sessionBikeId = activeSession.getBikeId();
            // User trying to return a bike they did not rent
            if (!sessionBikeId.equals(bikeId)) {
                throw new BikeNotRentedByUserException(user.getId(), bikeId);
            } else {
                endRentalSession(activeSession);
                return "{\"message\":\"OK\"}";
            }
        }
    }

    /**
     * Starts a new rental session.
     *
     * @param sessionId Unique Id for the session. Uses the Session Id generated by Spring Web for the request.
     * @param user      User for which a session is started.
     * @param bike      Bike that the User rented.
     */
    private void startRentalSession(String sessionId, User user, Bike bike) {
        // add info to session
        Long sessionStart = System.currentTimeMillis();
        sessionRepository.save(new Session(sessionId, user.getId(), user.getName(), bike.getBikeId(), bike.getFrame(), sessionStart, null));
        // mark Bike as rented
        bikeRepository.setBikeIsFree(bike.getBikeId(), false);
        // mark User to have rented
        userRepository.setUserHasRented(user.getId(), true);
    }

    /**
     * Ends a rental session.
     *
     * @param activeSession the session to be ended.
     */
    private void endRentalSession(Session activeSession) {
        // add timestamp to session end
        sessionRepository.setSessionEnd(activeSession.getSessionId(), System.currentTimeMillis());
        // mark bike as free
        bikeRepository.setBikeIsFree(activeSession.getBikeId(), true);
        // mark User to have stopped renting
        userRepository.setUserHasRented(activeSession.getUserId(), false);
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
