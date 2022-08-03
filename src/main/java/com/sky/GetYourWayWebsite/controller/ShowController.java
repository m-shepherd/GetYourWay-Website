package com.sky.GetYourWayWebsite.controller;

import com.sky.GetYourWayWebsite.domain.dto.Shows;
import com.sky.GetYourWayWebsite.service.ShowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://3.10.61.220:3000"})
public class ShowController {
    @Autowired
    private ShowServiceImpl showService;

    @GetMapping("/shows")
    public List<Shows> getShows() { return showService.getAllShows(); }
}
