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

public interface SectorRepository extends JpaRepository<Sector, UUID>, JpaSpecificationExecutor<Sector> {

    Optional<Sector> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Sector> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Sector> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Sector> findByTenantIdAndBaseStationId(String tenantId, UUID baseStationId, Pageable pageable);
    long countByTenantIdAndBaseStationId(String tenantId, UUID baseStationId);
    long countByTenantIdAndCriticality(String tenantId, SectorCriticality criticality);

    @Query("select e.status as status, count(e) as total from Sector e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Sector e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Sector> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
