package com.example.managebookingsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ManageBookingsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageBookingsServiceApplication.class, args);
    }

}
