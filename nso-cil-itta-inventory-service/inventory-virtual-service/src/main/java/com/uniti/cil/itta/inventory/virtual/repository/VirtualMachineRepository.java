package com.uniti.cil.itta.inventory.virtual.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, UUID>, JpaSpecificationExecutor<VirtualMachine> {

    Optional<VirtualMachine> findByTenantIdAndId(String tenantId, UUID id);

    Optional<VirtualMachine> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<VirtualMachine> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<VirtualMachine> findByTenantIdAndHypervisorId(String tenantId, UUID hypervisorId, Pageable pageable);
    long countByTenantIdAndHypervisorId(String tenantId, UUID hypervisorId);
    long countByTenantIdAndPowerState(String tenantId, VirtualMachinePowerState powerState);
    long countByTenantIdAndCriticality(String tenantId, VirtualMachineCriticality criticality);

    @Query("select e.status as status, count(e) as total from VirtualMachine e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from VirtualMachine e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<VirtualMachine> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
