package com.uniti.cil.itta.inventory.customer.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SlaProfileRepository extends JpaRepository<SlaProfile, UUID>, JpaSpecificationExecutor<SlaProfile> {

    Optional<SlaProfile> findByTenantIdAndId(String tenantId, UUID id);

    Optional<SlaProfile> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<SlaProfile> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndCriticality(String tenantId, SlaProfileCriticality criticality);

    @Query("select e.status as status, count(e) as total from SlaProfile e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from SlaProfile e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<SlaProfile> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
