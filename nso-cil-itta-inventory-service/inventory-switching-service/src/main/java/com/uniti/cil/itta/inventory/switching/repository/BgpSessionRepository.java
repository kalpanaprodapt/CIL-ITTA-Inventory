package com.uniti.cil.itta.inventory.switching.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BgpSessionRepository extends JpaRepository<BgpSession, UUID>, JpaSpecificationExecutor<BgpSession> {

    Optional<BgpSession> findByTenantIdAndId(String tenantId, UUID id);

    Optional<BgpSession> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<BgpSession> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<BgpSession> findByTenantIdAndLocalDeviceId(String tenantId, UUID localDeviceId, Pageable pageable);
    long countByTenantIdAndLocalDeviceId(String tenantId, UUID localDeviceId);
    long countByTenantIdAndSessionState(String tenantId, BgpSessionSessionState sessionState);
    long countByTenantIdAndCriticality(String tenantId, BgpSessionCriticality criticality);

    @Query("select e.status as status, count(e) as total from BgpSession e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from BgpSession e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<BgpSession> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
