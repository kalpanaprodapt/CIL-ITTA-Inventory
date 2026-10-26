package com.uniti.cil.itta.inventory.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Discovery and Reconciliation service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.discovery", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.discovery.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.discovery.repository")
public class DiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
    }
}
