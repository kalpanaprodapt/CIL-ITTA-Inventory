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

public interface DiscoveredAssetRepository extends JpaRepository<DiscoveredAsset, UUID>, JpaSpecificationExecutor<DiscoveredAsset> {

    Optional<DiscoveredAsset> findByTenantIdAndId(String tenantId, UUID id);

    Optional<DiscoveredAsset> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<DiscoveredAsset> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<DiscoveredAsset> findByTenantIdAndJobId(String tenantId, UUID jobId, Pageable pageable);
    long countByTenantIdAndJobId(String tenantId, UUID jobId);
    Page<DiscoveredAsset> findByTenantIdAndMatchedAssetId(String tenantId, UUID matchedAssetId, Pageable pageable);
    long countByTenantIdAndMatchedAssetId(String tenantId, UUID matchedAssetId);
    long countByTenantIdAndLifecycleStage(String tenantId, DiscoveredAssetLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from DiscoveredAsset e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from DiscoveredAsset e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<DiscoveredAsset> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
