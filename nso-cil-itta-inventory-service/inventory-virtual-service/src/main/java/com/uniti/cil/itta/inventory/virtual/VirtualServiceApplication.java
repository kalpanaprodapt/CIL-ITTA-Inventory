package com.uniti.cil.itta.inventory.virtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Virtual and Cloud service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.virtual", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.virtual.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.virtual.repository")
public class VirtualServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualServiceApplication.class, args);
    }
}
