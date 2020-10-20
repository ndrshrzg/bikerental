package org.ndrshrzg.bikerental.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.ndrshrzg.bikerental.model.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostReturnTest {

    private static final ObjectMapper om = new ObjectMapper();

    private static final String username_User1 = "Hans";
    private static final String password_User1 = "temp1234!";

    private static final String username_User2 = "Henry";
    private static final String password_User2 = "1234temp!";

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void returnRentedBikeResultsInSuccess() {
        // rent bike
        restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/rent/1", null, String.class);

        // return bike
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/return/1", null, String.class);

        // assert return success
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    @Test
    void returnRentedBikeReturnedBikeIsFree() throws Exception {
        // rent bike
        restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/rent/1", null, String.class);

        // return bike
        restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/return/1", null, String.class);

        // get bike info after return
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username_User1, password_User1)
                .getForEntity("/bikes/1", String.class);

        Bike bike = om.readValue(result.getBody(), Bike.class);

        // assert return success
        assertTrue(bike.isFree());

    }

    @Test
    void returnBikeByUserWithoutActiveSessionResultsInBadRequest() {
        // return bike without an active session (User initially has no active session)
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/return/1", null, String.class);

        // assert status code Bad Request
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void returnDifferentFreeBikeByUserWithActiveSessionResultsInBadRequest() throws Exception {
        // User1 rents bike so user has an active session
        restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/rent/1", null, String.class);

        // assert that bike 2 is free
        ResponseEntity<String> differentBikeData = restTemplate
                .withBasicAuth(username_User1, password_User1)
                .getForEntity("/bikes/2", String.class);

        Bike freeBike = om.readValue(differentBikeData.getBody(), Bike.class);
        assertTrue(freeBike.isFree());

        // try to return free bike
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/return/" + freeBike.getBikeId(), null, String.class);

        // assert status code Bad Request
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void returnBikeRentedByDifferentUserResultsInBadRequest() {
        // User1 rents bike 1
        restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/rent/1", null, String.class);

        // User2 rents bike 2
        restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/rent/2", null, String.class);

        // User to tries to return bike 1
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username_User2, password_User2)
                .postForEntity("/return/1", null, String.class);

        // assert status Bad Request
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

}
