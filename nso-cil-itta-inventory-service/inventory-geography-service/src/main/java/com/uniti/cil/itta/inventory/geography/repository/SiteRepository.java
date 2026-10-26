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

public interface SiteRepository extends JpaRepository<Site, UUID>, JpaSpecificationExecutor<Site> {

    Optional<Site> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Site> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Site> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Site> findByTenantIdAndCityId(String tenantId, UUID cityId, Pageable pageable);
    long countByTenantIdAndCityId(String tenantId, UUID cityId);
    long countByTenantIdAndSiteType(String tenantId, SiteSiteType siteType);
    long countByTenantIdAndLifecycleStage(String tenantId, SiteLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from Site e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Site e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Site> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
