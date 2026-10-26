package com.uniti.cil.itta.inventory.radio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Radio and Mobile service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.radio", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.radio.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.radio.repository")
public class RadioServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RadioServiceApplication.class, args);
    }
}
