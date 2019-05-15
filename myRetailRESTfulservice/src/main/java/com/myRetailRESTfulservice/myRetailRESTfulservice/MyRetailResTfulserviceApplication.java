package com.myRetailRESTfulservice.myRetailRESTfulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MyRetailResTfulserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyRetailResTfulserviceApplication.class, args);
    }

}
