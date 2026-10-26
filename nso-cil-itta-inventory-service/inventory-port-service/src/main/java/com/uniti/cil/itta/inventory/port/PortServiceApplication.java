package com.uniti.cil.itta.inventory.port;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Port and Interface service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.port", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.port.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.port.repository")
public class PortServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortServiceApplication.class, args);
    }
}
