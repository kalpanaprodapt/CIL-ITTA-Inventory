package com.uniti.cil.itta.inventory.capacity.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ThresholdPolicyRepository extends JpaRepository<ThresholdPolicy, UUID>, JpaSpecificationExecutor<ThresholdPolicy> {

    Optional<ThresholdPolicy> findByTenantIdAndId(String tenantId, UUID id);

    Optional<ThresholdPolicy> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<ThresholdPolicy> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndCriticality(String tenantId, ThresholdPolicyCriticality criticality);

    @Query("select e.status as status, count(e) as total from ThresholdPolicy e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from ThresholdPolicy e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<ThresholdPolicy> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
