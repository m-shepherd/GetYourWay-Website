package com.sky.GetYourWayWebsite.service;

import com.sky.GetYourWayWebsite.domain.dao.ShowRepository;
import com.sky.GetYourWayWebsite.domain.dto.Shows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class ShowServiceTest {
    @MockBean
    ShowRepository showRepository;

    @Autowired
    ShowServiceImpl showService;

    @Test
    @DisplayName("Test get all the shows")
    public void testGetAllShows() {
        List<Shows> shows = new ArrayList<>();
        Shows testShow = new Shows();
        testShow.setShowName("Test Show");
        testShow.setShowLocationName("My location");
        shows.add(testShow);
        Shows testShow2 = new Shows();
        testShow2.setShowName("Test Show 2");
        testShow2.setShowLocationName("My Second Location");
        shows.add(testShow2);

        doReturn(shows).when(showRepository).findAll();

        List<Shows> returnedShows = showService.getAllShows();
        Assertions.assertEquals(2, returnedShows.size());
        Assertions.assertEquals("Test Show", returnedShows.get(0).getShowName());
        Assertions.assertEquals("Test Show 2", returnedShows.get(1).getShowName());
    }
}
