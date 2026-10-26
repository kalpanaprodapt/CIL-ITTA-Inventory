package com.uniti.cil.itta.inventory.circuit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Circuits service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.circuit", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.circuit.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.circuit.repository")
public class CircuitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircuitServiceApplication.class, args);
    }
}
