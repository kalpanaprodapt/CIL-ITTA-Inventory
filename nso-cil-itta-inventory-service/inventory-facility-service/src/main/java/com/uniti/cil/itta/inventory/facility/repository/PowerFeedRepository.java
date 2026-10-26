package com.uniti.cil.itta.inventory.facility.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PowerFeedRepository extends JpaRepository<PowerFeed, UUID>, JpaSpecificationExecutor<PowerFeed> {

    Optional<PowerFeed> findByTenantIdAndId(String tenantId, UUID id);

    Optional<PowerFeed> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<PowerFeed> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<PowerFeed> findByTenantIdAndRoomId(String tenantId, UUID roomId, Pageable pageable);
    long countByTenantIdAndRoomId(String tenantId, UUID roomId);
    long countByTenantIdAndFeedType(String tenantId, PowerFeedFeedType feedType);
    long countByTenantIdAndCriticality(String tenantId, PowerFeedCriticality criticality);

    @Query("select e.status as status, count(e) as total from PowerFeed e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from PowerFeed e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<PowerFeed> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
