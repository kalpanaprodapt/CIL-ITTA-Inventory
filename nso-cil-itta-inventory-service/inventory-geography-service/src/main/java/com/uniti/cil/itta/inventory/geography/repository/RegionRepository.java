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

public interface RegionRepository extends JpaRepository<Region, UUID>, JpaSpecificationExecutor<Region> {

    Optional<Region> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Region> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Region> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Region> findByTenantIdAndCountryId(String tenantId, UUID countryId, Pageable pageable);
    long countByTenantIdAndCountryId(String tenantId, UUID countryId);
    long countByTenantIdAndRegionType(String tenantId, RegionRegionType regionType);
    long countByTenantIdAndCriticality(String tenantId, RegionCriticality criticality);

    @Query("select e.status as status, count(e) as total from Region e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Region e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Region> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
