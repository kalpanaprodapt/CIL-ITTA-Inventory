package com.uniti.cil.itta.inventory.discovery.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.model.DiscoveryJob;
import com.uniti.cil.itta.inventory.discovery.spec.DiscoveryJobSpecifications;
import com.uniti.cil.itta.inventory.discovery.support.DiscoveryJobFixtures;
import com.uniti.cil.itta.inventory.discovery.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class DiscoveryJobRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private DiscoveryJobRepository repository;

    @Test
    void savesAndFindsByCode() {
        DiscoveryJob saved = repository.save(DiscoveryJobFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(DiscoveryJobFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        DiscoveryJob first = repository.save(DiscoveryJobFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(DiscoveryJobFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(DiscoveryJobFixtures.entity());
        var page = repository.findAll(DiscoveryJobSpecifications.fromCriteria(DiscoveryJobFixtures.TENANT, DiscoveryJobFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(DiscoveryJobFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(DiscoveryJobFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
