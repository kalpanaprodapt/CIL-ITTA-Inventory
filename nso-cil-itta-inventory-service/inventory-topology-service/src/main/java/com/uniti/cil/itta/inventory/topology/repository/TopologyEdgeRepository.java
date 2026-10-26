package com.uniti.cil.itta.inventory.topology.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopologyEdgeRepository extends JpaRepository<TopologyEdge, UUID>, JpaSpecificationExecutor<TopologyEdge> {

    Optional<TopologyEdge> findByTenantIdAndId(String tenantId, UUID id);

    Optional<TopologyEdge> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<TopologyEdge> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<TopologyEdge> findByTenantIdAndViewId(String tenantId, UUID viewId, Pageable pageable);
    long countByTenantIdAndViewId(String tenantId, UUID viewId);
    Page<TopologyEdge> findByTenantIdAndSourceNodeId(String tenantId, UUID sourceNodeId, Pageable pageable);
    long countByTenantIdAndSourceNodeId(String tenantId, UUID sourceNodeId);
    Page<TopologyEdge> findByTenantIdAndTargetNodeId(String tenantId, UUID targetNodeId, Pageable pageable);
    long countByTenantIdAndTargetNodeId(String tenantId, UUID targetNodeId);
    long countByTenantIdAndLifecycleStage(String tenantId, TopologyEdgeLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from TopologyEdge e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from TopologyEdge e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<TopologyEdge> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
