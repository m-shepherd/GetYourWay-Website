package com.sky.GetYourWayWebsite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sky.GetYourWayWebsite.domain.dto.Users;
import com.sky.GetYourWayWebsite.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @MockBean
    private UserDetailsServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test getting all the users")
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

        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].username").value("Miles"))
                .andExpect(jsonPath("$[1].username").value("Michael"));
    }

    @Test
    @DisplayName("Test requesting a single user that does exist")
    public void testGetOneUserValid() throws Exception {
        Optional<Users> u1 = Optional.of(new Users());
        u1.get().setUsername("Michael");

        doReturn(u1).when(userService).findByUsername("Michael");

        mockMvc.perform(get("/users/Michael").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("Michael"));
    }

    @Test
    @DisplayName("Test requesting a single user that does not exist")
    public void testGetOneUserInvalid() throws Exception {
        Optional<Users> u1 = Optional.of(new Users());
        u1.get().setUsername("Michael");

        doReturn(u1).when(userService).findByUsername("Michael");

        mockMvc.perform(get("/users/Miles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Test find user by email that does exist")
    public void testCheckUserByEmailValid() throws Exception {
        Optional<Users> u1 = Optional.of(new Users());
        u1.get().setEmail("test@example.com");

        doReturn(u1).when(userService).findByEmail("test@example.com");

        mockMvc.perform(get("/email/getUserByEmail?email=test@example.com").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test find user by email that does exist")
    public void testCheckUserByEmailInvalid() throws Exception {
        doReturn(Optional.empty()).when(userService).findByEmail(anyString());

        mockMvc.perform(get("/email/getUserByEmail?email=michael@example.com")).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test update user with user that is present")
    public void testUpdateUserUserExists() throws Exception {
        Users u1 = new Users();
        u1.setUsername("michael");

        doReturn(Optional.of(u1)).when(userService).findByUsername("michael");
        doReturn(u1).when(userService).addUser(any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(u1))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Test update user with user that is not present")
    public void testUpdateUserUserNotExists() throws Exception {
        Users u1 = new Users();
        u1.setUsername("michael");

        doReturn(Optional.empty()).when(userService).findByUsername("michael");
        doReturn(u1).when(userService).addUser(any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(u1))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Test create user valid")
    public void testNewUserValid() throws Exception {
        Users u1 = new Users();
        u1.setUsername("michael");

        doReturn(Optional.empty()).when(userService).findByUsername("michael");
        doReturn(u1).when(userService).addUser(any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/signUp")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(u1))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
    @Test
    @DisplayName("Test create user already exists")
    public void testNewUserInvalid() throws Exception {
        Users u1 = new Users();
        u1.setUsername("michael");

        doReturn(Optional.of(u1)).when(userService).findByUsername("michael");
        doReturn(u1).when(userService).addUser(any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/signUp")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(u1))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }
}
