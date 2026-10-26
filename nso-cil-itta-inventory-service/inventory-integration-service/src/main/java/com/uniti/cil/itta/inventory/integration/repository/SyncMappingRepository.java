package com.uniti.cil.itta.inventory.integration.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SyncMappingRepository extends JpaRepository<SyncMapping, UUID>, JpaSpecificationExecutor<SyncMapping> {

    Optional<SyncMapping> findByTenantIdAndId(String tenantId, UUID id);

    Optional<SyncMapping> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<SyncMapping> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<SyncMapping> findByTenantIdAndJobId(String tenantId, UUID jobId, Pageable pageable);
    long countByTenantIdAndJobId(String tenantId, UUID jobId);

    @Query("select e.status as status, count(e) as total from SyncMapping e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from SyncMapping e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<SyncMapping> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
