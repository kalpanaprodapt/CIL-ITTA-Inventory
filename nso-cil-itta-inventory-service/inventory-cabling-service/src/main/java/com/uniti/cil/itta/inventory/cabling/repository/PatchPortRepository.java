package com.uniti.cil.itta.inventory.cabling.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatchPortRepository extends JpaRepository<PatchPort, UUID>, JpaSpecificationExecutor<PatchPort> {

    Optional<PatchPort> findByTenantIdAndId(String tenantId, UUID id);

    Optional<PatchPort> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<PatchPort> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<PatchPort> findByTenantIdAndPanelId(String tenantId, UUID panelId, Pageable pageable);
    long countByTenantIdAndPanelId(String tenantId, UUID panelId);
    Page<PatchPort> findByTenantIdAndConnectedCableId(String tenantId, UUID connectedCableId, Pageable pageable);
    long countByTenantIdAndConnectedCableId(String tenantId, UUID connectedCableId);
    long countByTenantIdAndLifecycleStage(String tenantId, PatchPortLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from PatchPort e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from PatchPort e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<PatchPort> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
