package com.uniti.cil.itta.inventory.switching.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RouteTargetRepository extends JpaRepository<RouteTarget, UUID>, JpaSpecificationExecutor<RouteTarget> {

    Optional<RouteTarget> findByTenantIdAndId(String tenantId, UUID id);

    Optional<RouteTarget> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<RouteTarget> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndRtType(String tenantId, RouteTargetRtType rtType);
    Page<RouteTarget> findByTenantIdAndVrfId(String tenantId, UUID vrfId, Pageable pageable);
    long countByTenantIdAndVrfId(String tenantId, UUID vrfId);
    long countByTenantIdAndLifecycleStage(String tenantId, RouteTargetLifecycleStage lifecycleStage);
    long countByTenantIdAndCriticality(String tenantId, RouteTargetCriticality criticality);

    @Query("select e.status as status, count(e) as total from RouteTarget e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from RouteTarget e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<RouteTarget> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
