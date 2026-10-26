package com.uniti.cil.itta.inventory.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Services service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.service", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.service.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.service.repository")
public class ServiceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceServiceApplication.class, args);
    }
}
