package com.uniti.cil.itta.inventory.discovery.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscoveryProfileRepository extends JpaRepository<DiscoveryProfile, UUID>, JpaSpecificationExecutor<DiscoveryProfile> {

    Optional<DiscoveryProfile> findByTenantIdAndId(String tenantId, UUID id);

    Optional<DiscoveryProfile> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<DiscoveryProfile> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndProtocol(String tenantId, DiscoveryProfileProtocol protocol);

    @Query("select e.status as status, count(e) as total from DiscoveryProfile e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from DiscoveryProfile e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<DiscoveryProfile> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
