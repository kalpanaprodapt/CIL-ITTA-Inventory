package com.uniti.cil.itta.inventory.change.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, UUID>, JpaSpecificationExecutor<WorkOrder> {

    Optional<WorkOrder> findByTenantIdAndId(String tenantId, UUID id);

    Optional<WorkOrder> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<WorkOrder> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<WorkOrder> findByTenantIdAndAssignedTechnicianId(String tenantId, UUID assignedTechnicianId, Pageable pageable);
    long countByTenantIdAndAssignedTechnicianId(String tenantId, UUID assignedTechnicianId);
    long countByTenantIdAndWorkState(String tenantId, WorkOrderWorkState workState);
    long countByTenantIdAndLifecycleStage(String tenantId, WorkOrderLifecycleStage lifecycleStage);
    long countByTenantIdAndCriticality(String tenantId, WorkOrderCriticality criticality);

    @Query("select e.status as status, count(e) as total from WorkOrder e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from WorkOrder e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<WorkOrder> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
