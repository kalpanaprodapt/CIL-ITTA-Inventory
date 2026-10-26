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

public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, UUID>, JpaSpecificationExecutor<CustomerAccount> {

    Optional<CustomerAccount> findByTenantIdAndId(String tenantId, UUID id);

    Optional<CustomerAccount> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<CustomerAccount> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<CustomerAccount> findByTenantIdAndCustomerId(String tenantId, UUID customerId, Pageable pageable);
    long countByTenantIdAndCustomerId(String tenantId, UUID customerId);
    long countByTenantIdAndBillingCycle(String tenantId, CustomerAccountBillingCycle billingCycle);
    long countByTenantIdAndLifecycleStage(String tenantId, CustomerAccountLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from CustomerAccount e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from CustomerAccount e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<CustomerAccount> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
