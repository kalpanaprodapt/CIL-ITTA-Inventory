package com.uniti.cil.itta.inventory.software;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Software and Licensing service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.software", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.software.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.software.repository")
public class SoftwareServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftwareServiceApplication.class, args);
    }
}
