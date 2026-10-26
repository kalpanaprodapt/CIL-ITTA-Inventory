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

public interface CircuitTerminationRepository extends JpaRepository<CircuitTermination, UUID>, JpaSpecificationExecutor<CircuitTermination> {

    Optional<CircuitTermination> findByTenantIdAndId(String tenantId, UUID id);

    Optional<CircuitTermination> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<CircuitTermination> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<CircuitTermination> findByTenantIdAndCircuitId(String tenantId, UUID circuitId, Pageable pageable);
    long countByTenantIdAndCircuitId(String tenantId, UUID circuitId);
    long countByTenantIdAndSide(String tenantId, CircuitTerminationSide side);
    Page<CircuitTermination> findByTenantIdAndPortId(String tenantId, UUID portId, Pageable pageable);
    long countByTenantIdAndPortId(String tenantId, UUID portId);

    @Query("select e.status as status, count(e) as total from CircuitTermination e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from CircuitTermination e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<CircuitTermination> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
