package com.jinax.pm_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PMBackendApplication {


    public static void main(String[] args) {
        SpringApplication.run(PMBackendApplication.class, args);
    }

}
