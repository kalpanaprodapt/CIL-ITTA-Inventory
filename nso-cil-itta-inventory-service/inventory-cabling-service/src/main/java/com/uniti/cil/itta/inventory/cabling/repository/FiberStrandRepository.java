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

public interface FiberStrandRepository extends JpaRepository<FiberStrand, UUID>, JpaSpecificationExecutor<FiberStrand> {

    Optional<FiberStrand> findByTenantIdAndId(String tenantId, UUID id);

    Optional<FiberStrand> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<FiberStrand> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<FiberStrand> findByTenantIdAndCableId(String tenantId, UUID cableId, Pageable pageable);
    long countByTenantIdAndCableId(String tenantId, UUID cableId);
    long countByTenantIdAndCriticality(String tenantId, FiberStrandCriticality criticality);

    @Query("select e.status as status, count(e) as total from FiberStrand e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from FiberStrand e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<FiberStrand> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
