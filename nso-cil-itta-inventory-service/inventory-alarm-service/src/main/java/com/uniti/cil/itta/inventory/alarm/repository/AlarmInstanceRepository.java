package com.uniti.cil.itta.inventory.alarm.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlarmInstanceRepository extends JpaRepository<AlarmInstance, UUID>, JpaSpecificationExecutor<AlarmInstance> {

    Optional<AlarmInstance> findByTenantIdAndId(String tenantId, UUID id);

    Optional<AlarmInstance> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<AlarmInstance> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<AlarmInstance> findByTenantIdAndDefinitionId(String tenantId, UUID definitionId, Pageable pageable);
    long countByTenantIdAndDefinitionId(String tenantId, UUID definitionId);
    long countByTenantIdAndAlarmState(String tenantId, AlarmInstanceAlarmState alarmState);
    long countByTenantIdAndLifecycleStage(String tenantId, AlarmInstanceLifecycleStage lifecycleStage);

    @Query("select e.status as status, count(e) as total from AlarmInstance e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from AlarmInstance e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<AlarmInstance> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
