package com.uniti.cil.itta.inventory.vendor.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SkuRepository extends JpaRepository<Sku, UUID>, JpaSpecificationExecutor<Sku> {

    Optional<Sku> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Sku> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Sku> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Sku> findByTenantIdAndModelId(String tenantId, UUID modelId, Pageable pageable);
    long countByTenantIdAndModelId(String tenantId, UUID modelId);
    long countByTenantIdAndCriticality(String tenantId, SkuCriticality criticality);

    @Query("select e.status as status, count(e) as total from Sku e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Sku e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Sku> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
