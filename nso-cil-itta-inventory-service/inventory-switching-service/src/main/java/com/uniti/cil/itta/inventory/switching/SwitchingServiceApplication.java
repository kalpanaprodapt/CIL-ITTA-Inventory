package com.uniti.cil.itta.inventory.switching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the L2 and L3 Routing service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.switching", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.switching.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.switching.repository")
public class SwitchingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwitchingServiceApplication.class, args);
    }
}
