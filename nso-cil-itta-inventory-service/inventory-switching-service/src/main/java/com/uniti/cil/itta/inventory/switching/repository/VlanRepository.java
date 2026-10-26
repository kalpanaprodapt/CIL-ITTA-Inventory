package com.uniti.cil.itta.inventory.switching.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VlanRepository extends JpaRepository<Vlan, UUID>, JpaSpecificationExecutor<Vlan> {

    Optional<Vlan> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Vlan> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Vlan> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndVlanType(String tenantId, VlanVlanType vlanType);
    Page<Vlan> findByTenantIdAndPoolId(String tenantId, UUID poolId, Pageable pageable);
    long countByTenantIdAndPoolId(String tenantId, UUID poolId);
    long countByTenantIdAndLifecycleStage(String tenantId, VlanLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from Vlan e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Vlan e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Vlan> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
