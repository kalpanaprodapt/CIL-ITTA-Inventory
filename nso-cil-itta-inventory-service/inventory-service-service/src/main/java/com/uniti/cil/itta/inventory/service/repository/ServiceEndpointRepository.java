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

public interface ServiceEndpointRepository extends JpaRepository<ServiceEndpoint, UUID>, JpaSpecificationExecutor<ServiceEndpoint> {

    Optional<ServiceEndpoint> findByTenantIdAndId(String tenantId, UUID id);

    Optional<ServiceEndpoint> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<ServiceEndpoint> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<ServiceEndpoint> findByTenantIdAndServiceId(String tenantId, UUID serviceId, Pageable pageable);
    long countByTenantIdAndServiceId(String tenantId, UUID serviceId);
    Page<ServiceEndpoint> findByTenantIdAndSiteId(String tenantId, UUID siteId, Pageable pageable);
    long countByTenantIdAndSiteId(String tenantId, UUID siteId);
    Page<ServiceEndpoint> findByTenantIdAndPortId(String tenantId, UUID portId, Pageable pageable);
    long countByTenantIdAndPortId(String tenantId, UUID portId);
    long countByTenantIdAndEndpointRole(String tenantId, ServiceEndpointEndpointRole endpointRole);
    long countByTenantIdAndLifecycleStage(String tenantId, ServiceEndpointLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from ServiceEndpoint e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from ServiceEndpoint e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<ServiceEndpoint> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
