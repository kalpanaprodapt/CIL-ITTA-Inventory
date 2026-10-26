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

public interface RoomRepository extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room> {

    Optional<Room> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Room> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Room> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Room> findByTenantIdAndFloorId(String tenantId, UUID floorId, Pageable pageable);
    long countByTenantIdAndFloorId(String tenantId, UUID floorId);
    long countByTenantIdAndRoomType(String tenantId, RoomRoomType roomType);
    long countByTenantIdAndLifecycleStage(String tenantId, RoomLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from Room e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Room e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Room> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
