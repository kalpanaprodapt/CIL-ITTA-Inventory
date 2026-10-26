package com.uniti.cil.itta.inventory.facility.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RackUnitRepository extends JpaRepository<RackUnit, UUID>, JpaSpecificationExecutor<RackUnit> {

    Optional<RackUnit> findByTenantIdAndId(String tenantId, UUID id);

    Optional<RackUnit> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<RackUnit> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<RackUnit> findByTenantIdAndRackId(String tenantId, UUID rackId, Pageable pageable);
    long countByTenantIdAndRackId(String tenantId, UUID rackId);
    Page<RackUnit> findByTenantIdAndMountedDeviceId(String tenantId, UUID mountedDeviceId, Pageable pageable);
    long countByTenantIdAndMountedDeviceId(String tenantId, UUID mountedDeviceId);
    long countByTenantIdAndFace(String tenantId, RackUnitFace face);
    long countByTenantIdAndCriticality(String tenantId, RackUnitCriticality criticality);

    @Query("select e.status as status, count(e) as total from RackUnit e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from RackUnit e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<RackUnit> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
