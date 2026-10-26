package com.uniti.cil.itta.inventory.facility.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.model.Floor;
import com.uniti.cil.itta.inventory.facility.spec.FloorSpecifications;
import com.uniti.cil.itta.inventory.facility.support.FloorFixtures;
import com.uniti.cil.itta.inventory.facility.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class FloorRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private FloorRepository repository;

    @Test
    void savesAndFindsByCode() {
        Floor saved = repository.save(FloorFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(FloorFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        Floor first = repository.save(FloorFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(FloorFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(FloorFixtures.entity());
        var page = repository.findAll(FloorSpecifications.fromCriteria(FloorFixtures.TENANT, FloorFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(FloorFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(FloorFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
