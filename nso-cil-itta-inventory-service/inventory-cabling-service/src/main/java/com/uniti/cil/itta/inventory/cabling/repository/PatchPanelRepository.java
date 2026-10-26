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

public interface PatchPanelRepository extends JpaRepository<PatchPanel, UUID>, JpaSpecificationExecutor<PatchPanel> {

    Optional<PatchPanel> findByTenantIdAndId(String tenantId, UUID id);

    Optional<PatchPanel> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<PatchPanel> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<PatchPanel> findByTenantIdAndRoomId(String tenantId, UUID roomId, Pageable pageable);
    long countByTenantIdAndRoomId(String tenantId, UUID roomId);
    Page<PatchPanel> findByTenantIdAndRackId(String tenantId, UUID rackId, Pageable pageable);
    long countByTenantIdAndRackId(String tenantId, UUID rackId);
    long countByTenantIdAndPanelType(String tenantId, PatchPanelPanelType panelType);
    long countByTenantIdAndLifecycleStage(String tenantId, PatchPanelLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from PatchPanel e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from PatchPanel e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<PatchPanel> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
