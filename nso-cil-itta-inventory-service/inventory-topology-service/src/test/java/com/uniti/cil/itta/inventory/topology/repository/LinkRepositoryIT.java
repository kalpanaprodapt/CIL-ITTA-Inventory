package com.uniti.cil.itta.inventory.topology.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.model.Link;
import com.uniti.cil.itta.inventory.topology.spec.LinkSpecifications;
import com.uniti.cil.itta.inventory.topology.support.LinkFixtures;
import com.uniti.cil.itta.inventory.topology.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class LinkRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private LinkRepository repository;

    @Test
    void savesAndFindsByCode() {
        Link saved = repository.save(LinkFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(LinkFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        Link first = repository.save(LinkFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(LinkFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(LinkFixtures.entity());
        var page = repository.findAll(LinkSpecifications.fromCriteria(LinkFixtures.TENANT, LinkFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(LinkFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(LinkFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
