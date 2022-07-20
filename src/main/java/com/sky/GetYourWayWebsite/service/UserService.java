package com.sky.GetYourWayWebsite.service;

import com.sky.GetYourWayWebsite.domain.dao.UserRepository;
import com.sky.GetYourWayWebsite.domain.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User addUser(User user) {
        if (!user.getUsername().equals("") && !user.getFirstName().equals("") &&
                !user.getLastName().equals("") &&
                !user.getEmail().equals("") && !user.getPassword() .equals("")) {
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
