package com.uniti.cil.itta.inventory.geography.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Site records. */
public interface SiteService {

    SiteResponse get(UUID id);

    SiteResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<SiteResponse> search(SiteSearchCriteria criteria, Pageable pageable);

    List<SiteSummary> summaries(LifecycleStatus status);

    SiteResponse create(SiteCreateRequest request);

    SiteResponse update(UUID id, SiteUpdateRequest request);

    SiteResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<SiteResponse> bulkCreate(List<SiteCreateRequest> requests);

    BulkResult<SiteResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(SiteSearchCriteria criteria, Writer writer);

    PageResponse<SiteResponse> findByCityId(UUID cityId, Pageable pageable);
}
