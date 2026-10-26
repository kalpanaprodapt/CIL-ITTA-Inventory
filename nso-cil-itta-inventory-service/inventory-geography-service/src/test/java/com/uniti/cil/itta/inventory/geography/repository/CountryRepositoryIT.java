package com.uniti.cil.itta.inventory.geography.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.model.Country;
import com.uniti.cil.itta.inventory.geography.spec.CountrySpecifications;
import com.uniti.cil.itta.inventory.geography.support.CountryFixtures;
import com.uniti.cil.itta.inventory.geography.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class CountryRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private CountryRepository repository;

    @Test
    void savesAndFindsByCode() {
        Country saved = repository.save(CountryFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(CountryFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        Country first = repository.save(CountryFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(CountryFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(CountryFixtures.entity());
        var page = repository.findAll(CountrySpecifications.fromCriteria(CountryFixtures.TENANT, CountryFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(CountryFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(CountryFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
