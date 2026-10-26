package com.uniti.cil.itta.inventory.geography.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BuildingRepository extends JpaRepository<Building, UUID>, JpaSpecificationExecutor<Building> {

    Optional<Building> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Building> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Building> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Building> findByTenantIdAndSiteId(String tenantId, UUID siteId, Pageable pageable);
    long countByTenantIdAndSiteId(String tenantId, UUID siteId);
    long countByTenantIdAndOwnership(String tenantId, BuildingOwnership ownership);
    long countByTenantIdAndLifecycleStage(String tenantId, BuildingLifecycleStage lifecycleStage);
    long countByTenantIdAndCriticality(String tenantId, BuildingCriticality criticality);

    @Query("select e.status as status, count(e) as total from Building e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Building e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Building> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
