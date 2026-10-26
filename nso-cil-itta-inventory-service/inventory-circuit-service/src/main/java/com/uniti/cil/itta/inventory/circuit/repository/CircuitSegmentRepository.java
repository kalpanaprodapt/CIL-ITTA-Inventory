package com.uniti.cil.itta.inventory.circuit.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CircuitSegmentRepository extends JpaRepository<CircuitSegment, UUID>, JpaSpecificationExecutor<CircuitSegment> {

    Optional<CircuitSegment> findByTenantIdAndId(String tenantId, UUID id);

    Optional<CircuitSegment> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<CircuitSegment> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<CircuitSegment> findByTenantIdAndCircuitId(String tenantId, UUID circuitId, Pageable pageable);
    long countByTenantIdAndCircuitId(String tenantId, UUID circuitId);
    Page<CircuitSegment> findByTenantIdAndATerminationId(String tenantId, UUID aTerminationId, Pageable pageable);
    long countByTenantIdAndATerminationId(String tenantId, UUID aTerminationId);
    Page<CircuitSegment> findByTenantIdAndZTerminationId(String tenantId, UUID zTerminationId, Pageable pageable);
    long countByTenantIdAndZTerminationId(String tenantId, UUID zTerminationId);
    long countByTenantIdAndCriticality(String tenantId, CircuitSegmentCriticality criticality);

    @Query("select e.status as status, count(e) as total from CircuitSegment e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from CircuitSegment e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<CircuitSegment> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
