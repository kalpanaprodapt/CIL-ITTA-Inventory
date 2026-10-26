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

public interface FanTrayRepository extends JpaRepository<FanTray, UUID>, JpaSpecificationExecutor<FanTray> {

    Optional<FanTray> findByTenantIdAndId(String tenantId, UUID id);

    Optional<FanTray> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<FanTray> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<FanTray> findByTenantIdAndChassisId(String tenantId, UUID chassisId, Pageable pageable);
    long countByTenantIdAndChassisId(String tenantId, UUID chassisId);
    long countByTenantIdAndAirflow(String tenantId, FanTrayAirflow airflow);
    long countByTenantIdAndLifecycleStage(String tenantId, FanTrayLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from FanTray e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from FanTray e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<FanTray> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
