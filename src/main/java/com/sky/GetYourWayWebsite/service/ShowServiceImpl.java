package com.sky.GetYourWayWebsite.service;

import com.sky.GetYourWayWebsite.domain.dao.ShowRepository;
import com.sky.GetYourWayWebsite.domain.dto.Shows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowServiceImpl {
    @Autowired
    ShowRepository showRepository;

    public List<Shows> getAllShows() { return showRepository.findAll(); }
}
