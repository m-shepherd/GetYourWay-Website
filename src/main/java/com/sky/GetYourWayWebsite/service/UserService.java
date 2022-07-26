package com.sky.GetYourWayWebsite.service;

import com.sky.GetYourWayWebsite.domain.dao.UserRepository;
import com.sky.GetYourWayWebsite.domain.dto.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users addUser(Users user) {
        if (!user.getUsername().equals("") && !user.getFirstName().equals("") &&
                !user.getLastName().equals("") &&
                !user.getEmail().equals("") && !user.getPassword() .equals("")) {
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Optional<Users> findByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

}
