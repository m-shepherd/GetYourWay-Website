package com.sky.GetYourWayWebsite.controller;

// Java Program to Create Rest Controller that
// Defines various API for Sending Mail


// Importing required classes

import com.sky.GetYourWayWebsite.email.EmailDetails;
import com.sky.GetYourWayWebsite.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Annotation
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://3.10.61.220:3000"})
public class EmailController {
    @Autowired private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/email/sendMail")
    public String sendMail(@RequestBody EmailDetails details) {
        return emailService.sendSimpleMail(details);
    }

    // Sending email with attachment
    @PostMapping("/email/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details) {
        return emailService.sendMailWithAttachment(details);
    }
}

