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

public interface ReconciliationRuleRepository extends JpaRepository<ReconciliationRule, UUID>, JpaSpecificationExecutor<ReconciliationRule> {

    Optional<ReconciliationRule> findByTenantIdAndId(String tenantId, UUID id);

    Optional<ReconciliationRule> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<ReconciliationRule> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndAction(String tenantId, ReconciliationRuleAction action);
    long countByTenantIdAndLifecycleStage(String tenantId, ReconciliationRuleLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from ReconciliationRule e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from ReconciliationRule e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<ReconciliationRule> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
