package com.sky.GetYourWayWebsite.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString
public class Shows {
    @Id
    private String showId;
    private String showName;
    private String showLocationName;
    private float showLocationLatitude;
    private float showLocationLongitude;
}
