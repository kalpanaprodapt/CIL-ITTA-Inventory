package com.uniti.cil.itta.inventory.ipam.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IpAddressRepository extends JpaRepository<IpAddress, UUID>, JpaSpecificationExecutor<IpAddress> {

    Optional<IpAddress> findByTenantIdAndId(String tenantId, UUID id);

    Optional<IpAddress> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<IpAddress> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<IpAddress> findByTenantIdAndSubnetId(String tenantId, UUID subnetId, Pageable pageable);
    long countByTenantIdAndSubnetId(String tenantId, UUID subnetId);
    Page<IpAddress> findByTenantIdAndAssignedToId(String tenantId, UUID assignedToId, Pageable pageable);
    long countByTenantIdAndAssignedToId(String tenantId, UUID assignedToId);
    long countByTenantIdAndAssignmentType(String tenantId, IpAddressAssignmentType assignmentType);

    @Query("select e.status as status, count(e) as total from IpAddress e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from IpAddress e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<IpAddress> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
