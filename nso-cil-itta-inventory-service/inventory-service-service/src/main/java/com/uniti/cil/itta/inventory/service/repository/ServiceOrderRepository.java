package com.uniti.cil.itta.inventory.service.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, UUID>, JpaSpecificationExecutor<ServiceOrder> {

    Optional<ServiceOrder> findByTenantIdAndId(String tenantId, UUID id);

    Optional<ServiceOrder> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<ServiceOrder> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<ServiceOrder> findByTenantIdAndCustomerId(String tenantId, UUID customerId, Pageable pageable);
    long countByTenantIdAndCustomerId(String tenantId, UUID customerId);
    long countByTenantIdAndOrderType(String tenantId, ServiceOrderOrderType orderType);
    long countByTenantIdAndCriticality(String tenantId, ServiceOrderCriticality criticality);

    @Query("select e.status as status, count(e) as total from ServiceOrder e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from ServiceOrder e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<ServiceOrder> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
