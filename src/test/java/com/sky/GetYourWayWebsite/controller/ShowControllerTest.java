package com.sky.GetYourWayWebsite.controller;

import com.sky.GetYourWayWebsite.domain.dto.Shows;
import com.sky.GetYourWayWebsite.service.ShowServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ShowControllerTest {
    @MockBean
    private ShowServiceImpl showService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test getting users")
    public void testGetShows() throws Exception {
        List<Shows> shows = new ArrayList<>();
        Shows testShow = new Shows();
        testShow.setShowName("Test Show");
        testShow.setShowLocationName("My location");
        shows.add(testShow);
        Shows testShow2 = new Shows();
        testShow2.setShowName("Test Show 2");
        testShow2.setShowLocationName("My Second Location");
        shows.add(testShow2);

        doReturn(shows).when(showService).getAllShows();

        mockMvc.perform(get("/shows").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].showName").value("Test Show"))
                .andExpect(jsonPath("$[1].showName").value("Test Show 2"));
    }
}
