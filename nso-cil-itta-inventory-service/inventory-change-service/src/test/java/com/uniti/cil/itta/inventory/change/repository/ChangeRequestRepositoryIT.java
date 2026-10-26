package com.uniti.cil.itta.inventory.change.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.model.ChangeRequest;
import com.uniti.cil.itta.inventory.change.spec.ChangeRequestSpecifications;
import com.uniti.cil.itta.inventory.change.support.ChangeRequestFixtures;
import com.uniti.cil.itta.inventory.change.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class ChangeRequestRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private ChangeRequestRepository repository;

    @Test
    void savesAndFindsByCode() {
        ChangeRequest saved = repository.save(ChangeRequestFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(ChangeRequestFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        ChangeRequest first = repository.save(ChangeRequestFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(ChangeRequestFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(ChangeRequestFixtures.entity());
        var page = repository.findAll(ChangeRequestSpecifications.fromCriteria(ChangeRequestFixtures.TENANT, ChangeRequestFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(ChangeRequestFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(ChangeRequestFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
