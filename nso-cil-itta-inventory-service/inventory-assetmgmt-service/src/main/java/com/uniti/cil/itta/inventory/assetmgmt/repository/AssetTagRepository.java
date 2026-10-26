package com.uniti.cil.itta.inventory.assetmgmt.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssetTagRepository extends JpaRepository<AssetTag, UUID>, JpaSpecificationExecutor<AssetTag> {

    Optional<AssetTag> findByTenantIdAndId(String tenantId, UUID id);

    Optional<AssetTag> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<AssetTag> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<AssetTag> findByTenantIdAndAssetId(String tenantId, UUID assetId, Pageable pageable);
    long countByTenantIdAndAssetId(String tenantId, UUID assetId);
    long countByTenantIdAndTagType(String tenantId, AssetTagTagType tagType);
    long countByTenantIdAndCriticality(String tenantId, AssetTagCriticality criticality);

    @Query("select e.status as status, count(e) as total from AssetTag e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from AssetTag e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<AssetTag> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
