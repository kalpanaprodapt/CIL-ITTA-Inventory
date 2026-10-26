package com.uniti.cil.itta.inventory.optical.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WdmSystemRepository extends JpaRepository<WdmSystem, UUID>, JpaSpecificationExecutor<WdmSystem> {

    Optional<WdmSystem> findByTenantIdAndId(String tenantId, UUID id);

    Optional<WdmSystem> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<WdmSystem> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndBand(String tenantId, WdmSystemBand band);
    Page<WdmSystem> findByTenantIdAndVendorId(String tenantId, UUID vendorId, Pageable pageable);
    long countByTenantIdAndVendorId(String tenantId, UUID vendorId);
    long countByTenantIdAndLifecycleStage(String tenantId, WdmSystemLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from WdmSystem e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from WdmSystem e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<WdmSystem> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
