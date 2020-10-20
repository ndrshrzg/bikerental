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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostRentTest {

    private static final ObjectMapper om = new ObjectMapper();

    private static final String username_User1 = "Hans";
    private static final String password_User1 = "temp1234!";

    private static final String username_User2 = "Henry";
    private static final String password_User2 = "1234temp!";

    @Autowired
    TestRestTemplate restTemplate;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void rentFreeBikeShouldReturnSuccess() {
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/rent/1", null, String.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void rentFreeBikRentedBikeIsNotFree() throws Exception {
        // rent bike
        restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/rent/1", null, String.class);

        // get bike info after rent
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username_User1, password_User1)
                .getForEntity("/bikes/1", String.class);

        Bike bike = om.readValue(result.getBody(), Bike.class);

        // assert return success
        assertFalse(bike.isFree());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void rentNotFreeBikeShouldReturnBadRequest() {
        // User2 rents bike
        restTemplate
                .withBasicAuth(username_User2, password_User2)
                .postForEntity("/rent/1", null, String.class);

        // User1 tries to rent the same bike
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/rent/1", null, String.class);

        // assert body contains informative message, status code indicates conflict
        if (result.getBody() != null) {
            assertTrue(result.getBody().contains("Choose a different bike"));
        }
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void rentMultipleBikesResultsInBadRequest() {
        // rent bike
        restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/rent/1", null, String.class);

        // try to rent same bike again
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username_User1, password_User1)
                .postForEntity("/rent/2", null, String.class);

        // assert body contains informative message, status code indicates conflict
        if (result.getBody() != null) {
            assertTrue(result.getBody().contains("has an active session"));
        }
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }

}
