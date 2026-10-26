package com.uniti.cil.itta.inventory.optical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Optical service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.optical", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.optical.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.optical.repository")
public class OpticalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpticalServiceApplication.class, args);
    }
}
