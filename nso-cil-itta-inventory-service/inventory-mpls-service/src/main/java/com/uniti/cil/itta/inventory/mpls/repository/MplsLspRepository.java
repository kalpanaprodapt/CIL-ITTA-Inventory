package com.uniti.cil.itta.inventory.mpls.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MplsLspRepository extends JpaRepository<MplsLsp, UUID>, JpaSpecificationExecutor<MplsLsp> {

    Optional<MplsLsp> findByTenantIdAndId(String tenantId, UUID id);

    Optional<MplsLsp> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<MplsLsp> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<MplsLsp> findByTenantIdAndHeadEndId(String tenantId, UUID headEndId, Pageable pageable);
    long countByTenantIdAndHeadEndId(String tenantId, UUID headEndId);
    Page<MplsLsp> findByTenantIdAndTailEndId(String tenantId, UUID tailEndId, Pageable pageable);
    long countByTenantIdAndTailEndId(String tenantId, UUID tailEndId);
    long countByTenantIdAndPathType(String tenantId, MplsLspPathType pathType);
    long countByTenantIdAndCriticality(String tenantId, MplsLspCriticality criticality);

    @Query("select e.status as status, count(e) as total from MplsLsp e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from MplsLsp e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<MplsLsp> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
