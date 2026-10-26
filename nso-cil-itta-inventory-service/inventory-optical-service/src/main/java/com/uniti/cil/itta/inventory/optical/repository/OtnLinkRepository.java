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

public interface OtnLinkRepository extends JpaRepository<OtnLink, UUID>, JpaSpecificationExecutor<OtnLink> {

    Optional<OtnLink> findByTenantIdAndId(String tenantId, UUID id);

    Optional<OtnLink> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<OtnLink> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<OtnLink> findByTenantIdAndANodeId(String tenantId, UUID aNodeId, Pageable pageable);
    long countByTenantIdAndANodeId(String tenantId, UUID aNodeId);
    Page<OtnLink> findByTenantIdAndZNodeId(String tenantId, UUID zNodeId, Pageable pageable);
    long countByTenantIdAndZNodeId(String tenantId, UUID zNodeId);
    long countByTenantIdAndOdu(String tenantId, OtnLinkOdu odu);
    long countByTenantIdAndLifecycleStage(String tenantId, OtnLinkLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from OtnLink e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from OtnLink e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<OtnLink> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
