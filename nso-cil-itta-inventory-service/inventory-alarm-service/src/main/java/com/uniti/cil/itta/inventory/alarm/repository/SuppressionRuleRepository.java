package com.uniti.cil.itta.inventory.alarm.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SuppressionRuleRepository extends JpaRepository<SuppressionRule, UUID>, JpaSpecificationExecutor<SuppressionRule> {

    Optional<SuppressionRule> findByTenantIdAndId(String tenantId, UUID id);

    Optional<SuppressionRule> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<SuppressionRule> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndCriticality(String tenantId, SuppressionRuleCriticality criticality);

    @Query("select e.status as status, count(e) as total from SuppressionRule e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from SuppressionRule e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<SuppressionRule> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
