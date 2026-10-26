package com.uniti.cil.itta.inventory.discovery.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReconciliationResultRepository extends JpaRepository<ReconciliationResult, UUID>, JpaSpecificationExecutor<ReconciliationResult> {

    Optional<ReconciliationResult> findByTenantIdAndId(String tenantId, UUID id);

    Optional<ReconciliationResult> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<ReconciliationResult> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<ReconciliationResult> findByTenantIdAndJobId(String tenantId, UUID jobId, Pageable pageable);
    long countByTenantIdAndJobId(String tenantId, UUID jobId);
    Page<ReconciliationResult> findByTenantIdAndRuleId(String tenantId, UUID ruleId, Pageable pageable);
    long countByTenantIdAndRuleId(String tenantId, UUID ruleId);
    long countByTenantIdAndOutcome(String tenantId, ReconciliationResultOutcome outcome);
    long countByTenantIdAndCriticality(String tenantId, ReconciliationResultCriticality criticality);

    @Query("select e.status as status, count(e) as total from ReconciliationResult e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from ReconciliationResult e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<ReconciliationResult> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
