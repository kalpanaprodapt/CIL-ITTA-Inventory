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

public interface ComplianceExceptionRepository extends JpaRepository<ComplianceException, UUID>, JpaSpecificationExecutor<ComplianceException> {

    Optional<ComplianceException> findByTenantIdAndId(String tenantId, UUID id);

    Optional<ComplianceException> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<ComplianceException> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<ComplianceException> findByTenantIdAndRuleId(String tenantId, UUID ruleId, Pageable pageable);
    long countByTenantIdAndRuleId(String tenantId, UUID ruleId);
    long countByTenantIdAndLifecycleStage(String tenantId, ComplianceExceptionLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from ComplianceException e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from ComplianceException e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<ComplianceException> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
