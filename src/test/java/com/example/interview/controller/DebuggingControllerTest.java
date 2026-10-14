package com.example.interview.controller;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DebuggingControllerTest {

    private static final String ENDPOINT = "/api/arrays/common-elements";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void preservesLeftOrderWhenReadingJson() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"left":[4,5,6,12,2,6,19,1],"right":[7,19,3,6]}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commonElements", contains(6, 19)));
    }

    @Test
    void acceptsEmptyArrays() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"left":[],"right":[6]}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commonElements").isEmpty());
    }

    @Test
    void acceptsSignedIntegerValues() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"left":[0,-1,2147483647,-2147483648],
                                 "right":[0,-1,2147483647,-2147483648]}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commonElements", contains(0, -1, 2147483647, -2147483648)));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{}",
            "null",
            "{\"left\":[],\"right\":null}",
            "{\"left\":null,\"right\":[]}",
            "{\"left\":[]}",
            "{\"right\":[]}",
            "{\"left\":[null],\"right\":[1]}",
            "{\"left\":[\"6\"],\"right\":[6]}",
            "{\"left\":[6.5],\"right\":[6]}",
            "{\"left\":[2147483648],\"right\":[6]}",
            "{\"left\":[6],\"right\":[6],\"extra\":true}",
            "{\"left\":[1],\"right\":"
    })
    void rejectsInvalidRequests(String request) throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsArraysAboveTheCurrentLimit() throws Exception {
        String request = objectMapper.writeValueAsString(Map.of(
                "left", Collections.nCopies(10_001, 1),
                "right", List.of(1)));

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
}