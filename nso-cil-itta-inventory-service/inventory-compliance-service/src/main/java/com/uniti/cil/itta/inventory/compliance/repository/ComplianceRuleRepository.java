package com.uniti.cil.itta.inventory.compliance.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComplianceRuleRepository extends JpaRepository<ComplianceRule, UUID>, JpaSpecificationExecutor<ComplianceRule> {

    Optional<ComplianceRule> findByTenantIdAndId(String tenantId, UUID id);

    Optional<ComplianceRule> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<ComplianceRule> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndFramework(String tenantId, ComplianceRuleFramework framework);
    long countByTenantIdAndSeverity(String tenantId, ComplianceRuleSeverity severity);
    long countByTenantIdAndCriticality(String tenantId, ComplianceRuleCriticality criticality);

    @Query("select e.status as status, count(e) as total from ComplianceRule e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from ComplianceRule e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<ComplianceRule> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
