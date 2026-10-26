package com.uniti.cil.itta.inventory.software.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SoftwareVersionRepository extends JpaRepository<SoftwareVersion, UUID>, JpaSpecificationExecutor<SoftwareVersion> {

    Optional<SoftwareVersion> findByTenantIdAndId(String tenantId, UUID id);

    Optional<SoftwareVersion> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<SoftwareVersion> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<SoftwareVersion> findByTenantIdAndImageId(String tenantId, UUID imageId, Pageable pageable);
    long countByTenantIdAndImageId(String tenantId, UUID imageId);
    long countByTenantIdAndReleaseTrain(String tenantId, SoftwareVersionReleaseTrain releaseTrain);
    long countByTenantIdAndCriticality(String tenantId, SoftwareVersionCriticality criticality);

    @Query("select e.status as status, count(e) as total from SoftwareVersion e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from SoftwareVersion e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<SoftwareVersion> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
