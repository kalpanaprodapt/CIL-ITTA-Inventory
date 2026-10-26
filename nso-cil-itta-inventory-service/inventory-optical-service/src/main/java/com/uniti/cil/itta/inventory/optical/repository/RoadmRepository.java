package com.uniti.cil.itta.inventory.optical.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoadmRepository extends JpaRepository<Roadm, UUID>, JpaSpecificationExecutor<Roadm> {

    Optional<Roadm> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Roadm> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Roadm> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Roadm> findByTenantIdAndSystemId(String tenantId, UUID systemId, Pageable pageable);
    long countByTenantIdAndSystemId(String tenantId, UUID systemId);
    long countByTenantIdAndCriticality(String tenantId, RoadmCriticality criticality);

    @Query("select e.status as status, count(e) as total from Roadm e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Roadm e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Roadm> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
