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

public interface VpnServiceRepository extends JpaRepository<VpnService, UUID>, JpaSpecificationExecutor<VpnService> {

    Optional<VpnService> findByTenantIdAndId(String tenantId, UUID id);

    Optional<VpnService> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<VpnService> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndVpnType(String tenantId, VpnServiceVpnType vpnType);
    Page<VpnService> findByTenantIdAndVrfId(String tenantId, UUID vrfId, Pageable pageable);
    long countByTenantIdAndVrfId(String tenantId, UUID vrfId);
    Page<VpnService> findByTenantIdAndCustomerId(String tenantId, UUID customerId, Pageable pageable);
    long countByTenantIdAndCustomerId(String tenantId, UUID customerId);
    long countByTenantIdAndTopology(String tenantId, VpnServiceTopology topology);
    long countByTenantIdAndCriticality(String tenantId, VpnServiceCriticality criticality);

    @Query("select e.status as status, count(e) as total from VpnService e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from VpnService e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<VpnService> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
