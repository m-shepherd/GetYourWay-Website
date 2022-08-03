package com.sky.GetYourWayWebsite.domain.dao;

import com.sky.GetYourWayWebsite.domain.dto.Shows;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Shows, Integer> {
}
