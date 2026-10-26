package com.uniti.cil.itta.inventory.facility;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Facility service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.facility", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.facility.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.facility.repository")
public class FacilityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacilityServiceApplication.class, args);
    }
}
