package com.sky.GetYourWayWebsite.service;

import com.sky.GetYourWayWebsite.domain.dao.UserRepository;
import com.sky.GetYourWayWebsite.domain.dto.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Users> possibleUser = userRepository.findUserByUsername(username);
        if (possibleUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        } else {
            Users user = possibleUser.get();
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), authorities);
        }
    }

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
