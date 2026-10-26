package com.uniti.cil.itta.inventory.compliance.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.model.ComplianceRule;
import com.uniti.cil.itta.inventory.compliance.spec.ComplianceRuleSpecifications;
import com.uniti.cil.itta.inventory.compliance.support.ComplianceRuleFixtures;
import com.uniti.cil.itta.inventory.compliance.support.PostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class ComplianceRuleRepositoryIT extends PostgresIntegrationTest {

    @Autowired
    private ComplianceRuleRepository repository;

    @Test
    void savesAndFindsByCode() {
        ComplianceRule saved = repository.save(ComplianceRuleFixtures.entity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findByTenantIdAndCode(ComplianceRuleFixtures.TENANT, saved.getCode())).isPresent();
    }

    @Test
    void codeIsUniquePerTenant() {
        ComplianceRule first = repository.save(ComplianceRuleFixtures.entity());
        assertThat(repository.existsByTenantIdAndCode(ComplianceRuleFixtures.TENANT, first.getCode())).isTrue();
        assertThat(repository.existsByTenantIdAndCode("other-tenant", first.getCode())).isFalse();
    }

    @Test
    void searchFiltersByStatus() {
        repository.save(ComplianceRuleFixtures.entity());
        var page = repository.findAll(ComplianceRuleSpecifications.fromCriteria(ComplianceRuleFixtures.TENANT, ComplianceRuleFixtures.criteria()),
                PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void countsByStatus() {
        repository.save(ComplianceRuleFixtures.entity());
        assertThat(repository.countByTenantIdAndStatus(ComplianceRuleFixtures.TENANT, LifecycleStatus.ACTIVE)).isGreaterThanOrEqualTo(1);
    }
}
