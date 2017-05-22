package com.ponomaryov.dispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DispatcherMain {

    public static void main(String[] args) {
        SpringApplication.run(DispatcherMain.class, args);
    }
}