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

public interface FieldTaskRepository extends JpaRepository<FieldTask, UUID>, JpaSpecificationExecutor<FieldTask> {

    Optional<FieldTask> findByTenantIdAndId(String tenantId, UUID id);

    Optional<FieldTask> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<FieldTask> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<FieldTask> findByTenantIdAndWorkOrderId(String tenantId, UUID workOrderId, Pageable pageable);
    long countByTenantIdAndWorkOrderId(String tenantId, UUID workOrderId);
    long countByTenantIdAndCriticality(String tenantId, FieldTaskCriticality criticality);

    @Query("select e.status as status, count(e) as total from FieldTask e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from FieldTask e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<FieldTask> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
