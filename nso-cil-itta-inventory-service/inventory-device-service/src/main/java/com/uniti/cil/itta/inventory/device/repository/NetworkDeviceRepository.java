package com.uniti.cil.itta.inventory.device.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NetworkDeviceRepository extends JpaRepository<NetworkDevice, UUID>, JpaSpecificationExecutor<NetworkDevice> {

    Optional<NetworkDevice> findByTenantIdAndId(String tenantId, UUID id);

    Optional<NetworkDevice> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<NetworkDevice> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndDeviceRole(String tenantId, NetworkDeviceDeviceRole deviceRole);
    Page<NetworkDevice> findByTenantIdAndModelId(String tenantId, UUID modelId, Pageable pageable);
    long countByTenantIdAndModelId(String tenantId, UUID modelId);
    Page<NetworkDevice> findByTenantIdAndRackUnitId(String tenantId, UUID rackUnitId, Pageable pageable);
    long countByTenantIdAndRackUnitId(String tenantId, UUID rackUnitId);
    long countByTenantIdAndLifecycleStage(String tenantId, NetworkDeviceLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from NetworkDevice e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from NetworkDevice e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<NetworkDevice> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
