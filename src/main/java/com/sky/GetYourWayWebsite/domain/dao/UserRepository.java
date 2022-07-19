package com.sky.GetYourWayWebsite.domain.dao;

import com.sky.GetYourWayWebsite.domain.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}

