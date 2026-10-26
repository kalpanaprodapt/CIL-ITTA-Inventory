package com.uniti.cil.itta.inventory.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Customers and SLA service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.customer", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.customer.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.customer.repository")
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
}
