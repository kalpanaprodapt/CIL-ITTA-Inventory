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

public interface TicketLinkRepository extends JpaRepository<TicketLink, UUID>, JpaSpecificationExecutor<TicketLink> {

    Optional<TicketLink> findByTenantIdAndId(String tenantId, UUID id);

    Optional<TicketLink> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<TicketLink> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<TicketLink> findByTenantIdAndAlarmId(String tenantId, UUID alarmId, Pageable pageable);
    long countByTenantIdAndAlarmId(String tenantId, UUID alarmId);
    long countByTenantIdAndTicketSystem(String tenantId, TicketLinkTicketSystem ticketSystem);
    long countByTenantIdAndCriticality(String tenantId, TicketLinkCriticality criticality);

    @Query("select e.status as status, count(e) as total from TicketLink e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from TicketLink e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<TicketLink> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
