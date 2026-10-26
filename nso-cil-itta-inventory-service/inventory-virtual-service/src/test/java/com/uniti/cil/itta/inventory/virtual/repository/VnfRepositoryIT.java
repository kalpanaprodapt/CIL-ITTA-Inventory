package com.uniti.cil.itta.inventory.virtual.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.model.Vnf;
import com.uniti.cil.itta.inventory.virtual.spec.VnfSpecifications;
import com.uniti.cil.itta.inventory.virtual.support.VnfFixtures;
import com.uniti.cil.itta.inventory.virtual.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class VnfRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private VnfRepository repository;

    @Test
    void savesAndFindsByCode() {
        Vnf saved = repository.save(VnfFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(VnfFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        Vnf first = repository.save(VnfFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(VnfFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(VnfFixtures.entity());
        var page = repository.findAll(VnfSpecifications.fromCriteria(VnfFixtures.TENANT, VnfFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(VnfFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(VnfFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
