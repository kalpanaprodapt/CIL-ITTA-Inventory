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

public interface ComplianceCheckRepository extends JpaRepository<ComplianceCheck, UUID>, JpaSpecificationExecutor<ComplianceCheck> {

    Optional<ComplianceCheck> findByTenantIdAndId(String tenantId, UUID id);

    Optional<ComplianceCheck> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<ComplianceCheck> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<ComplianceCheck> findByTenantIdAndRuleId(String tenantId, UUID ruleId, Pageable pageable);
    long countByTenantIdAndRuleId(String tenantId, UUID ruleId);
    long countByTenantIdAndResult(String tenantId, ComplianceCheckResult result);
    long countByTenantIdAndCriticality(String tenantId, ComplianceCheckCriticality criticality);

    @Query("select e.status as status, count(e) as total from ComplianceCheck e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from ComplianceCheck e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<ComplianceCheck> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
