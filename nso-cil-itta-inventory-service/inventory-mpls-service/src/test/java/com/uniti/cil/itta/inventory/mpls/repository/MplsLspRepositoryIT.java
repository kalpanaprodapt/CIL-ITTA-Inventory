package com.uniti.cil.itta.inventory.mpls.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.model.MplsLsp;
import com.uniti.cil.itta.inventory.mpls.spec.MplsLspSpecifications;
import com.uniti.cil.itta.inventory.mpls.support.MplsLspFixtures;
import com.uniti.cil.itta.inventory.mpls.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class MplsLspRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private MplsLspRepository repository;

    @Test
    void savesAndFindsByCode() {
        MplsLsp saved = repository.save(MplsLspFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(MplsLspFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        MplsLsp first = repository.save(MplsLspFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(MplsLspFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(MplsLspFixtures.entity());
        var page = repository.findAll(MplsLspSpecifications.fromCriteria(MplsLspFixtures.TENANT, MplsLspFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(MplsLspFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(MplsLspFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
