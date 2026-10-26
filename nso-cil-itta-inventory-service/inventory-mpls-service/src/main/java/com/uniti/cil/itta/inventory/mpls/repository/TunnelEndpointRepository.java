package com.uniti.cil.itta.inventory.mpls.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TunnelEndpointRepository extends JpaRepository<TunnelEndpoint, UUID>, JpaSpecificationExecutor<TunnelEndpoint> {

    Optional<TunnelEndpoint> findByTenantIdAndId(String tenantId, UUID id);

    Optional<TunnelEndpoint> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<TunnelEndpoint> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<TunnelEndpoint> findByTenantIdAndDeviceId(String tenantId, UUID deviceId, Pageable pageable);
    long countByTenantIdAndDeviceId(String tenantId, UUID deviceId);
    long countByTenantIdAndTunnelType(String tenantId, TunnelEndpointTunnelType tunnelType);
    long countByTenantIdAndLifecycleStage(String tenantId, TunnelEndpointLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from TunnelEndpoint e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from TunnelEndpoint e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<TunnelEndpoint> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
