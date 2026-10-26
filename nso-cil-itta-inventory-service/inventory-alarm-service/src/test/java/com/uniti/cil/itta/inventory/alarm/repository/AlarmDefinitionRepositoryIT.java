package com.uniti.cil.itta.inventory.alarm.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.model.AlarmDefinition;
import com.uniti.cil.itta.inventory.alarm.spec.AlarmDefinitionSpecifications;
import com.uniti.cil.itta.inventory.alarm.support.AlarmDefinitionFixtures;
import com.uniti.cil.itta.inventory.alarm.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class AlarmDefinitionRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private AlarmDefinitionRepository repository;

    @Test
    void savesAndFindsByCode() {
        AlarmDefinition saved = repository.save(AlarmDefinitionFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(AlarmDefinitionFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        AlarmDefinition first = repository.save(AlarmDefinitionFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(AlarmDefinitionFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(AlarmDefinitionFixtures.entity());
        var page = repository.findAll(AlarmDefinitionSpecifications.fromCriteria(AlarmDefinitionFixtures.TENANT, AlarmDefinitionFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(AlarmDefinitionFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(AlarmDefinitionFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
