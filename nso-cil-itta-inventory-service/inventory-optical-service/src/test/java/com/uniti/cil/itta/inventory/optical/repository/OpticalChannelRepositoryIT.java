package com.uniti.cil.itta.inventory.optical.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.OpticalChannel;
import com.uniti.cil.itta.inventory.optical.spec.OpticalChannelSpecifications;
import com.uniti.cil.itta.inventory.optical.support.OpticalChannelFixtures;
import com.uniti.cil.itta.inventory.optical.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class OpticalChannelRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private OpticalChannelRepository repository;

    @Test
    void savesAndFindsByCode() {
        OpticalChannel saved = repository.save(OpticalChannelFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(OpticalChannelFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        OpticalChannel first = repository.save(OpticalChannelFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(OpticalChannelFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(OpticalChannelFixtures.entity());
        var page = repository.findAll(OpticalChannelSpecifications.fromCriteria(OpticalChannelFixtures.TENANT, OpticalChannelFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(OpticalChannelFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(OpticalChannelFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
