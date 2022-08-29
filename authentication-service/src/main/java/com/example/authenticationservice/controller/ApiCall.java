package com.example.authenticationservice.controller;

import com.example.authenticationservice.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class ApiCall {

    private static RestTemplate restTemplate = new RestTemplate();

    private static final String urlTemplate = "http://%s:%s/";
    private static final String domain = "localhost";
    private static final String portUserService = "8083";
    private static final String USER_URL = String.format(urlTemplate + "api/users/", domain, portUserService);


    public static AppUser getAppUserByUsername(String username) {
        return restTemplate.getForObject(USER_URL + username, AppUser.class);
    }

}
