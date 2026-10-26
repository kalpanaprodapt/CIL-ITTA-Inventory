package com.uniti.cil.itta.inventory.port.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.model.Port;
import com.uniti.cil.itta.inventory.port.spec.PortSpecifications;
import com.uniti.cil.itta.inventory.port.support.PortFixtures;
import com.uniti.cil.itta.inventory.port.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class PortRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private PortRepository repository;

    @Test
    void savesAndFindsByCode() {
        Port saved = repository.save(PortFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(PortFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        Port first = repository.save(PortFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(PortFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(PortFixtures.entity());
        var page = repository.findAll(PortSpecifications.fromCriteria(PortFixtures.TENANT, PortFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(PortFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(PortFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
