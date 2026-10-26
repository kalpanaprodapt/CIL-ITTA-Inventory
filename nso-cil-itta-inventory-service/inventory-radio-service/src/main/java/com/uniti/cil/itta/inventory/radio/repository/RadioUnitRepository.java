package com.uniti.cil.itta.inventory.radio.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RadioUnitRepository extends JpaRepository<RadioUnit, UUID>, JpaSpecificationExecutor<RadioUnit> {

    Optional<RadioUnit> findByTenantIdAndId(String tenantId, UUID id);

    Optional<RadioUnit> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<RadioUnit> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<RadioUnit> findByTenantIdAndSiteId(String tenantId, UUID siteId, Pageable pageable);
    long countByTenantIdAndSiteId(String tenantId, UUID siteId);
    long countByTenantIdAndUnitType(String tenantId, RadioUnitUnitType unitType);
    long countByTenantIdAndLifecycleStage(String tenantId, RadioUnitLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from RadioUnit e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from RadioUnit e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<RadioUnit> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
