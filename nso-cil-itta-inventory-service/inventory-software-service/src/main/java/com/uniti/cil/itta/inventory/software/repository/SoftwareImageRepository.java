package com.uniti.cil.itta.inventory.software.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SoftwareImageRepository extends JpaRepository<SoftwareImage, UUID>, JpaSpecificationExecutor<SoftwareImage> {

    Optional<SoftwareImage> findByTenantIdAndId(String tenantId, UUID id);

    Optional<SoftwareImage> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<SoftwareImage> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<SoftwareImage> findByTenantIdAndVendorId(String tenantId, UUID vendorId, Pageable pageable);
    long countByTenantIdAndVendorId(String tenantId, UUID vendorId);
    long countByTenantIdAndLifecycleStage(String tenantId, SoftwareImageLifecycleStage lifecycleStage);
    long countByTenantIdAndCriticality(String tenantId, SoftwareImageCriticality criticality);

    @Query("select e.status as status, count(e) as total from SoftwareImage e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from SoftwareImage e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<SoftwareImage> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
