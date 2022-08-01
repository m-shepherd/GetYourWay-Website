package com.sky.GetYourWayWebsite.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://3.10.61.220:3000"})
public class NearestAirportController {
    private final String API_ADDRESS = "https://test.api.amadeus.com/v1/";
    private final String CLIENT_ID = "EtXMCRf8Z4CeoOTAV2IQZE6sxxasLHex";
    private final String CLIENT_SECRET = "mcLogSpqvZosETpS";


    @GetMapping("/flights/nearest")
    public JsonNode getNearestAirports(@RequestParam String latitude, @RequestParam String longitude) {
        String authorisationToken = getAuthorisationToken();
        if (authorisationToken == null) {
            return null;
        } else {
            try {
                URL url = new URL("https://test.api.amadeus.com/v1/reference-data/locations/airports?" +
                        "latitude=" + latitude +
                        "&longitude=" + longitude +
                        "&radius=250&page%5Blimit%5D=5&page%5Boffset%5D=0&sort=relevance");
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                http.setRequestProperty("Authorization", authorisationToken);

                return readResponse(http);

            } catch (Exception e) {
                return null;
            }
        }
    }

    private JsonNode readResponse(HttpURLConnection http) throws IOException {
        BufferedReader br = null;
        if (100 <= http.getResponseCode() && http.getResponseCode() <= 399) {
            br = new BufferedReader(new InputStreamReader(http.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
        }
        StringBuilder stringBuilder = new StringBuilder();
        String s;
        while ((s=br.readLine())!=null)
        {
            stringBuilder.append(s);
        }
        http.disconnect();

        String responseJsonString = stringBuilder.toString().replaceAll("\\s+","");
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readTree(responseJsonString);
    }

    private String getAuthorisationToken() {
        try {
            URL url = new URL("https://test.api.amadeus.com/v1/security/oauth2/token");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String data = "grant_type=client_credentials&client_id=EtXMCRf8Z4CeoOTAV2IQZE6sxxasLHex&client_secret=mcLogSpqvZosETpS";

            byte[] out = data.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = http.getOutputStream();
            stream.write(out);

            JsonNode jsonNode = readResponse(http);

            return jsonNode.get("token_type").toString().substring(1, jsonNode.get("token_type").toString().length() - 1)
                    + " " + jsonNode.get("access_token").toString().substring(1, jsonNode.get("access_token").toString().length() - 1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
