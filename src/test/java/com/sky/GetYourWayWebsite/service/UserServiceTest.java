package com.sky.GetYourWayWebsite.service;

import com.sky.GetYourWayWebsite.domain.dao.UserRepository;
import com.sky.GetYourWayWebsite.domain.dto.User;
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
    UserService userService;

    @Test
    @DisplayName("Test get all users success")
    public void getAllUsersTest(){
        List<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setUsername("Miles");
        users.add(u1);
        User u2 = new User();
        u2.setUsername("Michael");
        users.add(u2);
        User u3 = new User();
        u3.setUsername("Akash");
        users.add(u3);
        User u4 = new User();
        u4.setUsername("Dan");
        users.add(u4);

        doReturn(users).when(userRepository).findAll();

        List<User> returnedUsers = userService.getAllUsers();
        assertEquals(4,returnedUsers.size());
        verify(userRepository,times(1)).findAll();

    }

    @Test
    @DisplayName("ADD a user test success")
    public void addUserTest(){
        User u1 = new User();
        u1.setUsername("MickyShepShep");
        u1.setEmail("m@g.c");
        u1.setFirstName("Michael");
        u1.setLastName("Shepherd");
        u1.setPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(u1);

        User addedUser = userService.addUser(u1);
        assertEquals("MickyShepShep",addedUser.getUsername());
        verify(userRepository,times(1)).save(u1);
    }

    @Test
    @DisplayName("Get a user by username test success")
    public void getUserByUsernameTest(){
        User u1 = new User();
        u1.setUsername("MickyShepShep");
        u1.setEmail("m@g.c");
        u1.setFirstName("Michael");
        u1.setLastName("Shepherd");
        u1.setPassword("password");

        when(userRepository.findUserByUsername(any(String.class))).thenReturn(Optional.of(u1));

        Optional<User> optReturnedUser = userService.findByUsername(u1.getUsername());
        User returnedUser = optReturnedUser.get();
        assertEquals("MickyShepShep",returnedUser.getUsername());
        verify(userRepository,times(1)).findUserByUsername(u1.getUsername());
    }

}
