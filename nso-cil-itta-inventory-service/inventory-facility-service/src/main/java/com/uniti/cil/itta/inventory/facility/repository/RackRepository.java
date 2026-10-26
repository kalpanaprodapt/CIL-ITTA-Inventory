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

public interface RackRepository extends JpaRepository<Rack, UUID>, JpaSpecificationExecutor<Rack> {

    Optional<Rack> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Rack> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Rack> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Rack> findByTenantIdAndRoomId(String tenantId, UUID roomId, Pageable pageable);
    long countByTenantIdAndRoomId(String tenantId, UUID roomId);
    long countByTenantIdAndLifecycleStage(String tenantId, RackLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from Rack e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Rack e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Rack> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
