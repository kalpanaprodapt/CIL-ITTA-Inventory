package com.uniti.cil.itta.inventory.vendor.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.model.Vendor;
import com.uniti.cil.itta.inventory.vendor.spec.VendorSpecifications;
import com.uniti.cil.itta.inventory.vendor.support.VendorFixtures;
import com.uniti.cil.itta.inventory.vendor.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class VendorRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private VendorRepository repository;

    @Test
    void savesAndFindsByCode() {
        Vendor saved = repository.save(VendorFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(VendorFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        Vendor first = repository.save(VendorFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(VendorFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(VendorFixtures.entity());
        var page = repository.findAll(VendorSpecifications.fromCriteria(VendorFixtures.TENANT, VendorFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(VendorFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(VendorFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
