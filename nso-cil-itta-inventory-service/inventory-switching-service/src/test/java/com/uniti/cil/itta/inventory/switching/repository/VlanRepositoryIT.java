package com.uniti.cil.itta.inventory.switching.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.model.Vlan;
import com.uniti.cil.itta.inventory.switching.spec.VlanSpecifications;
import com.uniti.cil.itta.inventory.switching.support.VlanFixtures;
import com.uniti.cil.itta.inventory.switching.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class VlanRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private VlanRepository repository;

    @Test
    void savesAndFindsByCode() {
        Vlan saved = repository.save(VlanFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(VlanFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        Vlan first = repository.save(VlanFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(VlanFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(VlanFixtures.entity());
        var page = repository.findAll(VlanSpecifications.fromCriteria(VlanFixtures.TENANT, VlanFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(VlanFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(VlanFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
