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

public interface CableRepository extends JpaRepository<Cable, UUID>, JpaSpecificationExecutor<Cable> {

    Optional<Cable> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Cable> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Cable> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndCableType(String tenantId, CableCableType cableType);
    Page<Cable> findByTenantIdAndAEndPortId(String tenantId, UUID aEndPortId, Pageable pageable);
    long countByTenantIdAndAEndPortId(String tenantId, UUID aEndPortId);
    Page<Cable> findByTenantIdAndZEndPortId(String tenantId, UUID zEndPortId, Pageable pageable);
    long countByTenantIdAndZEndPortId(String tenantId, UUID zEndPortId);
    long countByTenantIdAndCriticality(String tenantId, CableCriticality criticality);

    @Query("select e.status as status, count(e) as total from Cable e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Cable e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Cable> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
