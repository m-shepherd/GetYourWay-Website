package com.sky.GetYourWayWebsite.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
public class Users {
    @Id
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
