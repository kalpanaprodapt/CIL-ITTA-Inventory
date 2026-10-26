package com.uniti.cil.itta.inventory.capacity.repository;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ForecastModelRepository extends JpaRepository<ForecastModel, UUID>, JpaSpecificationExecutor<ForecastModel> {

    Optional<ForecastModel> findByTenantIdAndId(String tenantId, UUID id);

    Optional<ForecastModel> findByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCode(String tenantId, String code);

    boolean existsByTenantIdAndCodeAndIdNot(String tenantId, String code, UUID id);

    List<ForecastModel> findByTenantIdAndExternalRef(String tenantId, String externalRef);

    long countByTenantIdAndStatus(String tenantId, LifecycleStatus status);

    long countByTenantIdAndAlgorithm(String tenantId, ForecastModelAlgorithm algorithm);
    long countByTenantIdAndCriticality(String tenantId, ForecastModelCriticality criticality);

    @Query("select e.status as status, count(e) as total from ForecastModel e where e.tenantId = :tenantId group by e.status")
    List<Object[]> countGroupedByStatus(@Param("tenantId") String tenantId);

    @Query("select e from ForecastModel e where e.tenantId = :tenantId and e.status = :status order by e.name")
    List<ForecastModel> findAllByStatusOrdered(@Param("tenantId") String tenantId, @Param("status") LifecycleStatus status);
}
