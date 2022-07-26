package com.sky.GetYourWayWebsite.domain.dao;

import com.sky.GetYourWayWebsite.domain.dto.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findUserByUsername(String username);
    @CrossOrigin
    Optional<Users> findUserByEmail(String email);

}

