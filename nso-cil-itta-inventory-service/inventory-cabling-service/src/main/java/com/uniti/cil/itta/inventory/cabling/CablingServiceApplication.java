package com.uniti.cil.itta.inventory.cabling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Cabling service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.cabling", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.cabling.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.cabling.repository")
public class CablingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CablingServiceApplication.class, args);
    }
}
