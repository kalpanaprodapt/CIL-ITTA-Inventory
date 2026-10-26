package com.uniti.cil.itta.inventory.topology.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinkRepository extends JpaRepository<Link, UUID>, JpaSpecificationExecutor<Link> {

    Optional<Link> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Link> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Link> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Link> findByTenantIdAndAInterfaceId(String tenantId, UUID aInterfaceId, Pageable pageable);
    long countByTenantIdAndAInterfaceId(String tenantId, UUID aInterfaceId);
    Page<Link> findByTenantIdAndZInterfaceId(String tenantId, UUID zInterfaceId, Pageable pageable);
    long countByTenantIdAndZInterfaceId(String tenantId, UUID zInterfaceId);
    long countByTenantIdAndLinkType(String tenantId, LinkLinkType linkType);
    long countByTenantIdAndCriticality(String tenantId, LinkCriticality criticality);

    @Query("select e.status as status, count(e) as total from Link e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Link e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Link> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
