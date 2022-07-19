package com.sky.GetYourWayWebsite.service;

import com.sky.GetYourWayWebsite.domain.dao.UserRepository;
import com.sky.GetYourWayWebsite.domain.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public List<User> getAllUsers(){
        List<User> users = userRepo.findAll();
        return users;
    }
}
