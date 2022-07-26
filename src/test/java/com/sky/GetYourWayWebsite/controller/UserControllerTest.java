package com.sky.GetYourWayWebsite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.GetYourWayWebsite.domain.dto.Users;
import com.sky.GetYourWayWebsite.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllUsers() throws Exception {
        List<Users> users = new ArrayList<>();
        Users u1 = new Users();
        u1.setUsername("Miles");
        users.add(u1);
        Users u2 = new Users();
        u2.setUsername("Michael");
        users.add(u2);
        Users u3 = new Users();
        u3.setUsername("Akash");
        users.add(u3);
        Users u4 = new Users();
        u4.setUsername("Dan");
        users.add(u4);

        doReturn(users).when(userService).getAllUsers();

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].username").value("Miles"))
                .andExpect(jsonPath("$[1].username").value("Michael"));
    }

    @Test
    public void testAddNewUser() throws Exception {
        Users u1 = new Users();
        u1.setUsername("MickyShepShep");
        u1.setEmail("m@g.c");
        u1.setFirstName("Michael");
        u1.setLastName("Shepherd");
        u1.setPassword("password");

        doReturn(new Users()).when(userService).addUser(u1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(u1))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());

    }

    @Test
    public void testGetUserByUsername() throws Exception{
        Users u1 = new Users();
        u1.setUsername("MickyShepShep");
        u1.setEmail("m@g.c");
        u1.setFirstName("Michael");
        u1.setLastName("Shepherd");
        u1.setPassword("password");

        doReturn(Optional.of(u1)).when(userService).findByUsername(u1.getUsername());

        mockMvc.perform(get("/users/MickyShepShep"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.username").value("MickyShepShep"));

    }
}
