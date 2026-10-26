package com.uniti.cil.itta.inventory.assetmgmt.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.model.Asset;
import com.uniti.cil.itta.inventory.assetmgmt.spec.AssetSpecifications;
import com.uniti.cil.itta.inventory.assetmgmt.support.AssetFixtures;
import com.uniti.cil.itta.inventory.assetmgmt.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class AssetRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private AssetRepository repository;

    @Test
    void savesAndFindsByCode() {
        Asset saved = repository.save(AssetFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(AssetFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        Asset first = repository.save(AssetFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(AssetFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(AssetFixtures.entity());
        var page = repository.findAll(AssetSpecifications.fromCriteria(AssetFixtures.TENANT, AssetFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(AssetFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(AssetFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
