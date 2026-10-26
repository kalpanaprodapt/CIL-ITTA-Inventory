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

public interface CityRepository extends JpaRepository<City, UUID>, JpaSpecificationExecutor<City> {

    Optional<City> findByTenantIdAndId(String tenantId, UUID id);

    Optional<City> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<City> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<City> findByTenantIdAndRegionId(String tenantId, UUID regionId, Pageable pageable);
    long countByTenantIdAndRegionId(String tenantId, UUID regionId);
    long countByTenantIdAndLifecycleStage(String tenantId, CityLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from City e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from City e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<City> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
