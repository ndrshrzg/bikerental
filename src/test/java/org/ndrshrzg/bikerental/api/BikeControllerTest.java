package org.ndrshrzg.bikerental.api;

import org.junit.jupiter.api.Test;
import org.ndrshrzg.bikerental.db.BikeRepository;
import org.ndrshrzg.bikerental.db.SessionRepository;
import org.ndrshrzg.bikerental.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BikeController.class)
class BikeControllerTest {

    @MockBean
    BikeRepository bikeRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    SessionRepository sessionRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getBikes() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/bikes")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth("Hans", "temp1234!", Charset.defaultCharset()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBikesUnauthorizedFails() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/bikes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getBikeByBikeId() {
        // todo
    }

    @Test
    void rentFreeBikeResultsInSuccess() {
        // todo
    }

    @Test
    void rentNotFreeBikeResultsInConflict() {
        // todo
    }

    @Test
    void rentMultipleBikesResultsInconflict() {
        // todo
    }

    @Test
    void returnBikeResultsInSuccess() {
        // todo
    }

    @Test
    void returnBikeNotRentedByUserResultsInBadRequest() {
        // todo
    }

    @Test
    void returnBikeWithNoActiveSessionResultsInBadRequest() {
        // todo
    }

}