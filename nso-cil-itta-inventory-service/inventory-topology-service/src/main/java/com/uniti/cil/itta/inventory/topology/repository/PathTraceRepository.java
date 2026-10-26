package com.uniti.cil.itta.inventory.topology.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PathTraceRepository extends JpaRepository<PathTrace, UUID>, JpaSpecificationExecutor<PathTrace> {

    Optional<PathTrace> findByTenantIdAndId(String tenantId, UUID id);

    Optional<PathTrace> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<PathTrace> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndLifecycleStage(String tenantId, PathTraceLifecycleStage lifecycleStage);
    long countByTenantIdAndCriticality(String tenantId, PathTraceCriticality criticality);

    @Query("select e.status as status, count(e) as total from PathTrace e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from PathTrace e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<PathTrace> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
