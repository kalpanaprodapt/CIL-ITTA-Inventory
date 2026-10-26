package com.uniti.cil.itta.inventory.customer.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerContactRepository extends JpaRepository<CustomerContact, UUID>, JpaSpecificationExecutor<CustomerContact> {

    Optional<CustomerContact> findByTenantIdAndId(String tenantId, UUID id);

    Optional<CustomerContact> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<CustomerContact> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<CustomerContact> findByTenantIdAndCustomerId(String tenantId, UUID customerId, Pageable pageable);
    long countByTenantIdAndCustomerId(String tenantId, UUID customerId);
    long countByTenantIdAndContactRole(String tenantId, CustomerContactContactRole contactRole);
    long countByTenantIdAndLifecycleStage(String tenantId, CustomerContactLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from CustomerContact e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from CustomerContact e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<CustomerContact> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
