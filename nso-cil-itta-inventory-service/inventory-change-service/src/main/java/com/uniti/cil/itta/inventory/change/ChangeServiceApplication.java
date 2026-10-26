package com.uniti.cil.itta.inventory.change;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Change and Work Management service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.change", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.change.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.change.repository")
public class ChangeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChangeServiceApplication.class, args);
    }
}
