package org.ndrshrzg.bikerental.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.ndrshrzg.bikerental.model.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetBikesTest {

    private static final ObjectMapper om = new ObjectMapper();

    private static final String username = "Hans";
    private static final String password = "temp1234!";

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void getBikeByExistingBikeIdShouldResultOK() {
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username, password)
                .getForEntity("/bikes/1", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getBikeByExistingBikeIdShouldReturnCorrectBike() throws Exception {
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username, password)
                .getForEntity("/bikes/1", String.class);

        Bike bike = om.readValue(result.getBody(), Bike.class);

        assertEquals(bike.getBikeId(), 1L);
        assertEquals(bike.getFrame(), "Wilier Cento1Air");

    }

    @Test
    void getBikeByNonExistingBikeIdShouldReturnNotFound() {
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username, password)
                .getForEntity("/bikes/99999", String.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    }

}
