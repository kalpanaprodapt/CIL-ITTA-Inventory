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

public interface HardwareModelRepository extends JpaRepository<HardwareModel, UUID>, JpaSpecificationExecutor<HardwareModel> {

    Optional<HardwareModel> findByTenantIdAndId(String tenantId, UUID id);

    Optional<HardwareModel> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<HardwareModel> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<HardwareModel> findByTenantIdAndVendorId(String tenantId, UUID vendorId, Pageable pageable);
    long countByTenantIdAndVendorId(String tenantId, UUID vendorId);
    long countByTenantIdAndLifecycleStage(String tenantId, HardwareModelLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from HardwareModel e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from HardwareModel e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<HardwareModel> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
