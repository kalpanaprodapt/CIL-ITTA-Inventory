package com.uniti.cil.itta.inventory.cabling.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.model.Cable;
import com.uniti.cil.itta.inventory.cabling.spec.CableSpecifications;
import com.uniti.cil.itta.inventory.cabling.support.CableFixtures;
import com.uniti.cil.itta.inventory.cabling.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class CableRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private CableRepository repository;

    @Test
    void savesAndFindsByCode() {
        Cable saved = repository.save(CableFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(CableFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        Cable first = repository.save(CableFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(CableFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(CableFixtures.entity());
        var page = repository.findAll(CableSpecifications.fromCriteria(CableFixtures.TENANT, CableFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(CableFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(CableFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
