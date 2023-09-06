package com.microservice.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Esta clase permite levantar el microservice de clients.
 * */
@SpringBootApplication
@EnableFeignClients
public class CustomerApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomerApplication.class, args);
  }

}
