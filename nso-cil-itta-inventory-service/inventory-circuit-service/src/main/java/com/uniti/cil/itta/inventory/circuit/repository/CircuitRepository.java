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

public interface CircuitRepository extends JpaRepository<Circuit, UUID>, JpaSpecificationExecutor<Circuit> {

    Optional<Circuit> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Circuit> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Circuit> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndCircuitType(String tenantId, CircuitCircuitType circuitType);
    Page<Circuit> findByTenantIdAndProviderId(String tenantId, UUID providerId, Pageable pageable);
    long countByTenantIdAndProviderId(String tenantId, UUID providerId);
    Page<Circuit> findByTenantIdAndASiteId(String tenantId, UUID aSiteId, Pageable pageable);
    long countByTenantIdAndASiteId(String tenantId, UUID aSiteId);
    Page<Circuit> findByTenantIdAndZSiteId(String tenantId, UUID zSiteId, Pageable pageable);
    long countByTenantIdAndZSiteId(String tenantId, UUID zSiteId);
    long countByTenantIdAndLifecycleStage(String tenantId, CircuitLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from Circuit e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Circuit e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Circuit> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
