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

public interface SupportContractRepository extends JpaRepository<SupportContract, UUID>, JpaSpecificationExecutor<SupportContract> {

    Optional<SupportContract> findByTenantIdAndId(String tenantId, UUID id);

    Optional<SupportContract> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<SupportContract> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<SupportContract> findByTenantIdAndVendorId(String tenantId, UUID vendorId, Pageable pageable);
    long countByTenantIdAndVendorId(String tenantId, UUID vendorId);
    long countByTenantIdAndCoverageLevel(String tenantId, SupportContractCoverageLevel coverageLevel);
    long countByTenantIdAndCriticality(String tenantId, SupportContractCriticality criticality);

    @Query("select e.status as status, count(e) as total from SupportContract e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from SupportContract e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<SupportContract> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
