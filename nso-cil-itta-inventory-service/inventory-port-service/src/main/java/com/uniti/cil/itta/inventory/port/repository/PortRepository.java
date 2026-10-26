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

public interface PortRepository extends JpaRepository<Port, UUID>, JpaSpecificationExecutor<Port> {

    Optional<Port> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Port> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Port> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Port> findByTenantIdAndDeviceId(String tenantId, UUID deviceId, Pageable pageable);
    long countByTenantIdAndDeviceId(String tenantId, UUID deviceId);
    Page<Port> findByTenantIdAndCardId(String tenantId, UUID cardId, Pageable pageable);
    long countByTenantIdAndCardId(String tenantId, UUID cardId);
    long countByTenantIdAndMediaType(String tenantId, PortMediaType mediaType);
    long countByTenantIdAndAdminState(String tenantId, PortAdminState adminState);
    long countByTenantIdAndCriticality(String tenantId, PortCriticality criticality);

    @Query("select e.status as status, count(e) as total from Port e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Port e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Port> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
