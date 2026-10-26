package com.uniti.cil.itta.inventory.optical.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OpticalChannelRepository extends JpaRepository<OpticalChannel, UUID>, JpaSpecificationExecutor<OpticalChannel> {

    Optional<OpticalChannel> findByTenantIdAndId(String tenantId, UUID id);

    Optional<OpticalChannel> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<OpticalChannel> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndGridType(String tenantId, OpticalChannelGridType gridType);
    Page<OpticalChannel> findByTenantIdAndSystemId(String tenantId, UUID systemId, Pageable pageable);
    long countByTenantIdAndSystemId(String tenantId, UUID systemId);
    long countByTenantIdAndCriticality(String tenantId, OpticalChannelCriticality criticality);

    @Query("select e.status as status, count(e) as total from OpticalChannel e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from OpticalChannel e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<OpticalChannel> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
