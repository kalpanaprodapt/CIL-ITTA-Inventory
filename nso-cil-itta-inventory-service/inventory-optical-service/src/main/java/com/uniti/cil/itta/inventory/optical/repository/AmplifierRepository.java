package com.uniti.cil.itta.inventory.optical.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AmplifierRepository extends JpaRepository<Amplifier, UUID>, JpaSpecificationExecutor<Amplifier> {

    Optional<Amplifier> findByTenantIdAndId(String tenantId, UUID id);

    Optional<Amplifier> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<Amplifier> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    Page<Amplifier> findByTenantIdAndSystemId(String tenantId, UUID systemId, Pageable pageable);
    long countByTenantIdAndSystemId(String tenantId, UUID systemId);
    long countByTenantIdAndAmpType(String tenantId, AmplifierAmpType ampType);
    long countByTenantIdAndLifecycleStage(String tenantId, AmplifierLifecycleStage lifecycleStage);
    long countByTenantIdAndCriticality(String tenantId, AmplifierCriticality criticality);

    @Query("select e.status as status, count(e) as total from Amplifier e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from Amplifier e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<Amplifier> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
