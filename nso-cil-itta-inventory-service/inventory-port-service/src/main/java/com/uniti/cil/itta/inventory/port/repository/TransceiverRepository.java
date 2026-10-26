package com.uniti.cil.itta.inventory.port.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransceiverRepository extends JpaRepository<Transceiver, UUID>, JpaSpecificationExecutor<Transceiver> {

    Optional<Transceiver> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Transceiver> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Transceiver> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Transceiver> findByTenantIdAndPortId(String tenantId, UUID portId, Pageable pageable);
    long countByTenantIdAndPortId(String tenantId, UUID portId);
    long countByTenantIdAndFormFactor(String tenantId, TransceiverFormFactor formFactor);
    long countByTenantIdAndLifecycleStage(String tenantId, TransceiverLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from Transceiver e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Transceiver e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Transceiver> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
