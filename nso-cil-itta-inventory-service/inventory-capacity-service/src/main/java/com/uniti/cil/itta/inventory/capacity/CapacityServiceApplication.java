package com.uniti.cil.itta.inventory.capacity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Capacity Planning service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.capacity", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.capacity.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.capacity.repository")
public class CapacityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapacityServiceApplication.class, args);
    }
}
