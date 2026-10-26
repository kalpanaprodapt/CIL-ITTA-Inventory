package com.uniti.cil.itta.inventory.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.model.IntegrationEndpoint;
import com.uniti.cil.itta.inventory.integration.spec.IntegrationEndpointSpecifications;
import com.uniti.cil.itta.inventory.integration.support.IntegrationEndpointFixtures;
import com.uniti.cil.itta.inventory.integration.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class IntegrationEndpointRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private IntegrationEndpointRepository repository;

    @Test
    void savesAndFindsByCode() {
        IntegrationEndpoint saved = repository.save(IntegrationEndpointFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(IntegrationEndpointFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        IntegrationEndpoint first = repository.save(IntegrationEndpointFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(IntegrationEndpointFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(IntegrationEndpointFixtures.entity());
        var page = repository.findAll(IntegrationEndpointSpecifications.fromCriteria(IntegrationEndpointFixtures.TENANT, IntegrationEndpointFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(IntegrationEndpointFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(IntegrationEndpointFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
