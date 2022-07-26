package com.sky.GetYourWayWebsite.service;

import com.sky.GetYourWayWebsite.domain.dao.UserRepository;
import com.sky.GetYourWayWebsite.domain.dto.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl userService;

    @Test
    @DisplayName("Test get all users success")
    public void getAllUsersTest(){
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

        doReturn(users).when(userRepository).findAll();

        List<Users> returnedUsers = userService.getAllUsers();
        assertEquals(4,returnedUsers.size());
        verify(userRepository,times(1)).findAll();

    }

    @Test
    @DisplayName("ADD a user test success")
    public void addUserTest(){
        Users u1 = new Users();
        u1.setUsername("MickyShepShep");
        u1.setEmail("m@g.c");
        u1.setFirstName("Michael");
        u1.setLastName("Shepherd");
        u1.setPassword("password");

        when(userRepository.save(any(Users.class))).thenReturn(u1);

        Users addedUser = userService.addUser(u1);
        assertEquals("MickyShepShep",addedUser.getUsername());
        verify(userRepository,times(1)).save(u1);
    }

    @Test
    @DisplayName("Get a user by username test success")
    public void getUserByUsernameTest(){
        Users u1 = new Users();
        u1.setUsername("MickyShepShep");
        u1.setEmail("m@g.c");
        u1.setFirstName("Michael");
        u1.setLastName("Shepherd");
        u1.setPassword("password");

        when(userRepository.findUserByUsername(any(String.class))).thenReturn(Optional.of(u1));

        Optional<Users> optReturnedUser = userService.findByUsername(u1.getUsername());
        Users returnedUser = optReturnedUser.get();
        assertEquals("MickyShepShep",returnedUser.getUsername());
        verify(userRepository,times(1)).findUserByUsername(u1.getUsername());
    }

}
