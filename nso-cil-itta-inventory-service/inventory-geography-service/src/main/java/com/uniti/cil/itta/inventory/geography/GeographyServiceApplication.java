package com.uniti.cil.itta.inventory.geography;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Geography service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.geography", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.geography.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.geography.repository")
public class GeographyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeographyServiceApplication.class, args);
    }
}
