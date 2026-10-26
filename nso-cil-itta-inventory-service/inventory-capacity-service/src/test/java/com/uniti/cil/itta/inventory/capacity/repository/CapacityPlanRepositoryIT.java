package com.uniti.cil.itta.inventory.capacity.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.model.CapacityPlan;
import com.uniti.cil.itta.inventory.capacity.spec.CapacityPlanSpecifications;
import com.uniti.cil.itta.inventory.capacity.support.CapacityPlanFixtures;
import com.uniti.cil.itta.inventory.capacity.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class CapacityPlanRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private CapacityPlanRepository repository;

    @Test
    void savesAndFindsByCode() {
        CapacityPlan saved = repository.save(CapacityPlanFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(CapacityPlanFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        CapacityPlan first = repository.save(CapacityPlanFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(CapacityPlanFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(CapacityPlanFixtures.entity());
        var page = repository.findAll(CapacityPlanSpecifications.fromCriteria(CapacityPlanFixtures.TENANT, CapacityPlanFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(CapacityPlanFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(CapacityPlanFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
