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

public interface LicenseKeyRepository extends JpaRepository<LicenseKey, UUID>, JpaSpecificationExecutor<LicenseKey> {

    Optional<LicenseKey> findByTenantIdAndId(String tenantId, UUID id);

    Optional<LicenseKey> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<LicenseKey> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<LicenseKey> findByTenantIdAndPoolId(String tenantId, UUID poolId, Pageable pageable);
    long countByTenantIdAndPoolId(String tenantId, UUID poolId);
    Page<LicenseKey> findByTenantIdAndAssignedDeviceId(String tenantId, UUID assignedDeviceId, Pageable pageable);
    long countByTenantIdAndAssignedDeviceId(String tenantId, UUID assignedDeviceId);
    long countByTenantIdAndLifecycleStage(String tenantId, LicenseKeyLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from LicenseKey e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from LicenseKey e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<LicenseKey> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
