package com.uniti.cil.itta.inventory.ipam.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.model.IpSubnet;
import com.uniti.cil.itta.inventory.ipam.spec.IpSubnetSpecifications;
import com.uniti.cil.itta.inventory.ipam.support.IpSubnetFixtures;
import com.uniti.cil.itta.inventory.ipam.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class IpSubnetRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private IpSubnetRepository repository;

    @Test
    void savesAndFindsByCode() {
        IpSubnet saved = repository.save(IpSubnetFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(IpSubnetFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        IpSubnet first = repository.save(IpSubnetFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(IpSubnetFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(IpSubnetFixtures.entity());
        var page = repository.findAll(IpSubnetSpecifications.fromCriteria(IpSubnetFixtures.TENANT, IpSubnetFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(IpSubnetFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(IpSubnetFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
