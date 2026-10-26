package com.uniti.cil.itta.inventory.virtual.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface K8sClusterRepository extends JpaRepository<K8sCluster, UUID>, JpaSpecificationExecutor<K8sCluster> {

    Optional<K8sCluster> findByTenantIdAndId(String tenantId, UUID id);

    Optional<K8sCluster> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<K8sCluster> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<K8sCluster> findByTenantIdAndCloudAccountId(String tenantId, UUID cloudAccountId, Pageable pageable);
    long countByTenantIdAndCloudAccountId(String tenantId, UUID cloudAccountId);
    long countByTenantIdAndLifecycleStage(String tenantId, K8sClusterLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from K8sCluster e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from K8sCluster e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<K8sCluster> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
