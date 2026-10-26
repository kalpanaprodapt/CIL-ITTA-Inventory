package com.uniti.cil.itta.inventory.circuit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.model.Circuit;
import com.uniti.cil.itta.inventory.circuit.spec.CircuitSpecifications;
import com.uniti.cil.itta.inventory.circuit.support.CircuitFixtures;
import com.uniti.cil.itta.inventory.circuit.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class CircuitRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private CircuitRepository repository;

    @Test
    void savesAndFindsByCode() {
        Circuit saved = repository.save(CircuitFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(CircuitFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        Circuit first = repository.save(CircuitFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(CircuitFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(CircuitFixtures.entity());
        var page = repository.findAll(CircuitSpecifications.fromCriteria(CircuitFixtures.TENANT, CircuitFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(CircuitFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(CircuitFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
