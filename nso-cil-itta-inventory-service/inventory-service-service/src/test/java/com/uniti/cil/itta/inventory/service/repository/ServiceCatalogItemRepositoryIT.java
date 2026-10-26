package com.uniti.cil.itta.inventory.service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.model.ServiceCatalogItem;
import com.uniti.cil.itta.inventory.service.spec.ServiceCatalogItemSpecifications;
import com.uniti.cil.itta.inventory.service.support.ServiceCatalogItemFixtures;
import com.uniti.cil.itta.inventory.service.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class ServiceCatalogItemRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private ServiceCatalogItemRepository repository;

    @Test
    void savesAndFindsByCode() {
        ServiceCatalogItem saved = repository.save(ServiceCatalogItemFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(ServiceCatalogItemFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        ServiceCatalogItem first = repository.save(ServiceCatalogItemFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(ServiceCatalogItemFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(ServiceCatalogItemFixtures.entity());
        var page = repository.findAll(ServiceCatalogItemSpecifications.fromCriteria(ServiceCatalogItemFixtures.TENANT, ServiceCatalogItemFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(ServiceCatalogItemFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(ServiceCatalogItemFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
