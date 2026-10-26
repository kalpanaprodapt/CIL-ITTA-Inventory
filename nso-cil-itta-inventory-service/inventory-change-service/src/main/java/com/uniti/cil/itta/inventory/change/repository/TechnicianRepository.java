package com.uniti.cil.itta.inventory.change.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TechnicianRepository extends JpaRepository<Technician, UUID>, JpaSpecificationExecutor<Technician> {

    Optional<Technician> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Technician> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Technician> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Technician> findByTenantIdAndHomeSiteId(String tenantId, UUID homeSiteId, Pageable pageable);
    long countByTenantIdAndHomeSiteId(String tenantId, UUID homeSiteId);
    long countByTenantIdAndCriticality(String tenantId, TechnicianCriticality criticality);

    @Query("select e.status as status, count(e) as total from Technician e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Technician e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Technician> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
