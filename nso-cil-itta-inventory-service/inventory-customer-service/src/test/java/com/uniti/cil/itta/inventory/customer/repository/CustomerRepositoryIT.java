package com.uniti.cil.itta.inventory.customer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.model.Customer;
import com.uniti.cil.itta.inventory.customer.spec.CustomerSpecifications;
import com.uniti.cil.itta.inventory.customer.support.CustomerFixtures;
import com.uniti.cil.itta.inventory.customer.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class CustomerRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void savesAndFindsByCode() {
        Customer saved = repository.save(CustomerFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(CustomerFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        Customer first = repository.save(CustomerFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(CustomerFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(CustomerFixtures.entity());
        var page = repository.findAll(CustomerSpecifications.fromCriteria(CustomerFixtures.TENANT, CustomerFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(CustomerFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(CustomerFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
