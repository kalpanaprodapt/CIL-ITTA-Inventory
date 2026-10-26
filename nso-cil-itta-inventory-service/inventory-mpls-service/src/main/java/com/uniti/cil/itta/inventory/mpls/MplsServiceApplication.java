package com.uniti.cil.itta.inventory.mpls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the MPLS and VPN service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.mpls", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.mpls.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.mpls.repository")
public class MplsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MplsServiceApplication.class, args);
    }
}
