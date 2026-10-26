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

public interface TopologyNodeRepository extends JpaRepository<TopologyNode, UUID>, JpaSpecificationExecutor<TopologyNode> {

    Optional<TopologyNode> findByTenantIdAndId(String tenantId, UUID id);

    Optional<TopologyNode> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<TopologyNode> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<TopologyNode> findByTenantIdAndViewId(String tenantId, UUID viewId, Pageable pageable);
    long countByTenantIdAndViewId(String tenantId, UUID viewId);
    long countByTenantIdAndCriticality(String tenantId, TopologyNodeCriticality criticality);

    @Query("select e.status as status, count(e) as total from TopologyNode e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from TopologyNode e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<TopologyNode> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
