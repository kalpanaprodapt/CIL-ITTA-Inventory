package com.uniti.cil.itta.inventory.assetmgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Entry point of the Asset and Finance service. */
@SpringBootApplication(scanBasePackages = {"com.uniti.cil.itta.inventory.assetmgmt", "com.uniti.cil.itta.inventory.common"})
@EntityScan(basePackages = {"com.uniti.cil.itta.inventory.assetmgmt.model", "com.uniti.cil.itta.inventory.common.model"})
@EnableJpaRepositories(basePackages = "com.uniti.cil.itta.inventory.assetmgmt.repository")
public class AssetmgmtServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetmgmtServiceApplication.class, args);
    }
}
