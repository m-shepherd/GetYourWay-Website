package com.sky.GetYourWayWebsite.domain.dao;

import com.sky.GetYourWayWebsite.domain.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

}

