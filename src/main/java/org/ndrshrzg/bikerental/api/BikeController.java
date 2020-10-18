package org.ndrshrzg.bikerental.api;

import org.ndrshrzg.bikerental.db.BikeRepository;
import org.ndrshrzg.bikerental.db.SessionRepository;
import org.ndrshrzg.bikerental.db.UserRepository;
import org.ndrshrzg.bikerental.ex.BikeAlreadyRentedException;
import org.ndrshrzg.bikerental.ex.BikeNotFoundException;
import org.ndrshrzg.bikerental.ex.MethodNotImplementedException;
import org.ndrshrzg.bikerental.ex.UserHasActiveSessionException;
import org.ndrshrzg.bikerental.model.Bike;
import org.ndrshrzg.bikerental.model.Session;
import org.ndrshrzg.bikerental.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class BikeController {

    private final BikeRepository bikeRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(BikeController.class);

    BikeController(BikeRepository bikeRepository, SessionRepository sessionRepository, UserRepository userRepository) {
        this.bikeRepository = bikeRepository;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/bikes")
    List<Bike> getBikes() {
        return bikeRepository.findAll();
    }

    @GetMapping("/bikes/{bikeId}")
    Bike getBikeByBikeId(@PathVariable Long bikeId) {
        return bikeRepository.findById(bikeId).orElseThrow(() -> new BikeNotFoundException(bikeId));
    }

    @PostMapping("/rent/{bikeId}")
    String rentBikeByBikeId(@PathVariable Long bikeId, HttpServletRequest request) {

        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new BikeNotFoundException(bikeId));

        String sessionId = request.getSession().getId();
        String userName = request.getRemoteUser();

        User user = userRepository.findByName(userName);

        if (userHasActiveSession(user.getId())) {
            throw new UserHasActiveSessionException(user.getId());
        }

        if (bike.isFree()) {
            // add info to session
            Long sessionStart = System.currentTimeMillis();
            sessionRepository.save(new Session(sessionId, user.getId(), user.getName(), bikeId, bike.getFrame(), sessionStart, null));
            // mark bike as rented
            bikeRepository.setBikeFreeToFalse(bikeId);
            // return Ok response
            // todo beautify response
            return "OK";
        } else {
            throw new BikeAlreadyRentedException(bikeId);
        }

    }

    @PostMapping("/return/{bikeId}")
    String returnBikeByBikeId(@PathVariable("bikeId") Long bikeId) {
        // todo implement
        throw new MethodNotImplementedException();
    }


    private boolean userHasActiveSession(Long userId) {

        Session session = sessionRepository.findByUserId(userId);
        if (session == null) {
            logger.info("No session found.");
            return false;
        } else {
            if (session.getSessionEnd() == null) {
                logger.info("User has active session " + session.getSessionId());
                return true;
            } else {
                logger.info("User has no active session.");
                return false;
            }
        }

    }


}
