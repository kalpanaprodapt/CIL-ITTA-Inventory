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

public interface SpliceRepository extends JpaRepository<Splice, UUID>, JpaSpecificationExecutor<Splice> {

    Optional<Splice> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Splice> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Splice> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Splice> findByTenantIdAndAStrandId(String tenantId, UUID aStrandId, Pageable pageable);
    long countByTenantIdAndAStrandId(String tenantId, UUID aStrandId);
    Page<Splice> findByTenantIdAndZStrandId(String tenantId, UUID zStrandId, Pageable pageable);
    long countByTenantIdAndZStrandId(String tenantId, UUID zStrandId);
    long countByTenantIdAndLifecycleStage(String tenantId, SpliceLifecycleStage lifecycleStage);
    long countByTenantIdAndCriticality(String tenantId, SpliceCriticality criticality);

    @Query("select e.status as status, count(e) as total from Splice e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Splice e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Splice> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
