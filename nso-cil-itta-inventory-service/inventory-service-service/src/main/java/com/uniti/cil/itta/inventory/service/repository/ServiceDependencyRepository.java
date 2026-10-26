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

public interface ServiceDependencyRepository extends JpaRepository<ServiceDependency, UUID>, JpaSpecificationExecutor<ServiceDependency> {

    Optional<ServiceDependency> findByTenantIdAndId(String tenantId, UUID id);

    Optional<ServiceDependency> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<ServiceDependency> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<ServiceDependency> findByTenantIdAndServiceId(String tenantId, UUID serviceId, Pageable pageable);
    long countByTenantIdAndServiceId(String tenantId, UUID serviceId);
    Page<ServiceDependency> findByTenantIdAndDependsOnId(String tenantId, UUID dependsOnId, Pageable pageable);
    long countByTenantIdAndDependsOnId(String tenantId, UUID dependsOnId);
    long countByTenantIdAndDependencyType(String tenantId, ServiceDependencyDependencyType dependencyType);
    long countByTenantIdAndCriticality(String tenantId, ServiceDependencyCriticality criticality);

    @Query("select e.status as status, count(e) as total from ServiceDependency e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from ServiceDependency e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<ServiceDependency> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
