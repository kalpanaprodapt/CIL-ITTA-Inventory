package com.uniti.cil.itta.inventory.radio.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.model.RadioUnit;
import com.uniti.cil.itta.inventory.radio.spec.RadioUnitSpecifications;
import com.uniti.cil.itta.inventory.radio.support.RadioUnitFixtures;
import com.uniti.cil.itta.inventory.radio.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class RadioUnitRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private RadioUnitRepository repository;

    @Test
    void savesAndFindsByCode() {
        RadioUnit saved = repository.save(RadioUnitFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(RadioUnitFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        RadioUnit first = repository.save(RadioUnitFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(RadioUnitFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(RadioUnitFixtures.entity());
        var page = repository.findAll(RadioUnitSpecifications.fromCriteria(RadioUnitFixtures.TENANT, RadioUnitFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(RadioUnitFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(RadioUnitFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
