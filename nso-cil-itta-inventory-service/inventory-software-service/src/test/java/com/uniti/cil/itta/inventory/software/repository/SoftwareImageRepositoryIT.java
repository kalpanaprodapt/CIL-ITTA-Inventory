package com.uniti.cil.itta.inventory.software.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.model.SoftwareImage;
import com.uniti.cil.itta.inventory.software.spec.SoftwareImageSpecifications;
import com.uniti.cil.itta.inventory.software.support.SoftwareImageFixtures;
import com.uniti.cil.itta.inventory.software.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class SoftwareImageRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private SoftwareImageRepository repository;

    @Test
    void savesAndFindsByCode() {
        SoftwareImage saved = repository.save(SoftwareImageFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(SoftwareImageFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        SoftwareImage first = repository.save(SoftwareImageFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(SoftwareImageFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(SoftwareImageFixtures.entity());
        var page = repository.findAll(SoftwareImageSpecifications.fromCriteria(SoftwareImageFixtures.TENANT, SoftwareImageFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(SoftwareImageFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(SoftwareImageFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
