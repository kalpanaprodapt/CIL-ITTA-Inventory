package com.uniti.cil.itta.inventory.software.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatchRepository extends JpaRepository<Patch, UUID>, JpaSpecificationExecutor<Patch> {

    Optional<Patch> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Patch> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Patch> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Patch> findByTenantIdAndVersionId(String tenantId, UUID versionId, Pageable pageable);
    long countByTenantIdAndVersionId(String tenantId, UUID versionId);
    long countByTenantIdAndSeverity(String tenantId, PatchSeverity severity);
    long countByTenantIdAndCriticality(String tenantId, PatchCriticality criticality);

    @Query("select e.status as status, count(e) as total from Patch e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Patch e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Patch> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
