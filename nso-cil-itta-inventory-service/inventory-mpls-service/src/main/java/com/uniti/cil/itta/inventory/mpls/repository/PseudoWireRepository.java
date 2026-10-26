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

public interface PseudoWireRepository extends JpaRepository<PseudoWire, UUID>, JpaSpecificationExecutor<PseudoWire> {

    Optional<PseudoWire> findByTenantIdAndId(String tenantId, UUID id);

    Optional<PseudoWire> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<PseudoWire> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<PseudoWire> findByTenantIdAndAEndId(String tenantId, UUID aEndId, Pageable pageable);
    long countByTenantIdAndAEndId(String tenantId, UUID aEndId);
    Page<PseudoWire> findByTenantIdAndZEndId(String tenantId, UUID zEndId, Pageable pageable);
    long countByTenantIdAndZEndId(String tenantId, UUID zEndId);

    @Query("select e.status as status, count(e) as total from PseudoWire e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from PseudoWire e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<PseudoWire> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
