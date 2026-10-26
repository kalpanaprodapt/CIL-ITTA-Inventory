package com.uniti.cil.itta.inventory.change.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Technician records. */
public interface TechnicianService {

    TechnicianResponse get(UUID id);

    TechnicianResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<TechnicianResponse> search(TechnicianSearchCriteria criteria, Pageable pageable);

    List<TechnicianSummary> summaries(LifecycleStatus status);

    TechnicianResponse create(TechnicianCreateRequest request);

    TechnicianResponse update(UUID id, TechnicianUpdateRequest request);

    TechnicianResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<TechnicianResponse> bulkCreate(List<TechnicianCreateRequest> requests);

    BulkResult<TechnicianResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(TechnicianSearchCriteria criteria, Writer writer);

    PageResponse<TechnicianResponse> findByHomeSiteId(UUID homeSiteId, Pageable pageable);
}
