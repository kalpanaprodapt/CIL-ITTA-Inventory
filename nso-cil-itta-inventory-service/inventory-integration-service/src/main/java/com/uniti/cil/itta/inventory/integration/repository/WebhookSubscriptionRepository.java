package com.uniti.cil.itta.inventory.integration.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WebhookSubscriptionRepository extends JpaRepository<WebhookSubscription, UUID>, JpaSpecificationExecutor<WebhookSubscription> {

    Optional<WebhookSubscription> findByTenantIdAndId(String tenantId, UUID id);

    Optional<WebhookSubscription> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<WebhookSubscription> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndLifecycleStage(String tenantId, WebhookSubscriptionLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from WebhookSubscription e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from WebhookSubscription e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<WebhookSubscription> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
