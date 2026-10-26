package com.uniti.cil.itta.inventory.topology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Topology and Links service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.topology", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.topology.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.topology.repository")
public class TopologyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopologyServiceApplication.class, args);
    }
}
