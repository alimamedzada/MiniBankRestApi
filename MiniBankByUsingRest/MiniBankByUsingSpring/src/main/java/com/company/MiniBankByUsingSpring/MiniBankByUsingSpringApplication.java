package com.company.MiniBankByUsingSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.company"})
public class MiniBankByUsingSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniBankByUsingSpringApplication.class, args);
    }

}
