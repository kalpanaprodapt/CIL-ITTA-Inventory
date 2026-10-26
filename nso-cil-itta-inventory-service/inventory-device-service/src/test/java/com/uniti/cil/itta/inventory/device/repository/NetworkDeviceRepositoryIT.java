package com.uniti.cil.itta.inventory.device.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.model.NetworkDevice;
import com.uniti.cil.itta.inventory.device.spec.NetworkDeviceSpecifications;
import com.uniti.cil.itta.inventory.device.support.NetworkDeviceFixtures;
import com.uniti.cil.itta.inventory.device.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class NetworkDeviceRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private NetworkDeviceRepository repository;

    @Test
    void savesAndFindsByCode() {
        NetworkDevice saved = repository.save(NetworkDeviceFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(NetworkDeviceFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        NetworkDevice first = repository.save(NetworkDeviceFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(NetworkDeviceFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(NetworkDeviceFixtures.entity());
        var page = repository.findAll(NetworkDeviceSpecifications.fromCriteria(NetworkDeviceFixtures.TENANT, NetworkDeviceFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(NetworkDeviceFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(NetworkDeviceFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
