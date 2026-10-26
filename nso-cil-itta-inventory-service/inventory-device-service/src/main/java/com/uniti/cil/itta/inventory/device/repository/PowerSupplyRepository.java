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

public interface PowerSupplyRepository extends JpaRepository<PowerSupply, UUID>, JpaSpecificationExecutor<PowerSupply> {

    Optional<PowerSupply> findByTenantIdAndId(String tenantId, UUID id);

    Optional<PowerSupply> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<PowerSupply> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<PowerSupply> findByTenantIdAndChassisId(String tenantId, UUID chassisId, Pageable pageable);
    long countByTenantIdAndChassisId(String tenantId, UUID chassisId);
    Page<PowerSupply> findByTenantIdAndFeedId(String tenantId, UUID feedId, Pageable pageable);
    long countByTenantIdAndFeedId(String tenantId, UUID feedId);
    long countByTenantIdAndCriticality(String tenantId, PowerSupplyCriticality criticality);

    @Query("select e.status as status, count(e) as total from PowerSupply e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from PowerSupply e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<PowerSupply> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
