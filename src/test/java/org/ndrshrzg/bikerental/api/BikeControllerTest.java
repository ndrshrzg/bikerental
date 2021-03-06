package org.ndrshrzg.bikerental.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BikeControllerTest {

    private static final String username = "Hans";
    private static final String password = "temp1234!";
    private static final String wrongPassword = "wrongpwd";

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void isAuthenticated_getBikesShouldResultOK() {
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username, password)
                .getForEntity("/bikes", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void wrongAuthentication_getBikesShouldResultUnauthorized() {
        ResponseEntity<String> result = restTemplate
                .withBasicAuth(username, wrongPassword)
                .getForEntity("/bikes", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void noAuthentication_getBikesShouldResultUnauthorized() {
        ResponseEntity<String> result = restTemplate
                .getForEntity("/bikes", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

}