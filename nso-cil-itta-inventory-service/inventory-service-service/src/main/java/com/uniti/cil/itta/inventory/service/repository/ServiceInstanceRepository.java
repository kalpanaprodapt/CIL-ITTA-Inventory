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

public interface ServiceInstanceRepository extends JpaRepository<ServiceInstance, UUID>, JpaSpecificationExecutor<ServiceInstance> {

    Optional<ServiceInstance> findByTenantIdAndId(String tenantId, UUID id);

    Optional<ServiceInstance> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<ServiceInstance> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<ServiceInstance> findByTenantIdAndCatalogItemId(String tenantId, UUID catalogItemId, Pageable pageable);
    long countByTenantIdAndCatalogItemId(String tenantId, UUID catalogItemId);
    Page<ServiceInstance> findByTenantIdAndCustomerId(String tenantId, UUID customerId, Pageable pageable);
    long countByTenantIdAndCustomerId(String tenantId, UUID customerId);
    long countByTenantIdAndServiceState(String tenantId, ServiceInstanceServiceState serviceState);
    long countByTenantIdAndLifecycleStage(String tenantId, ServiceInstanceLifecycleStage lifecycleStage);
    long countByTenantIdAndCriticality(String tenantId, ServiceInstanceCriticality criticality);

    @Query("select e.status as status, count(e) as total from ServiceInstance e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from ServiceInstance e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<ServiceInstance> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
