package com.uniti.cil.itta.inventory.ipam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the IP Management service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.ipam", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.ipam.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.ipam.repository")
public class IpamServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IpamServiceApplication.class, args);
    }
}
