package com.uniti.cil.itta.inventory.software.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Software Version records. */
public interface SoftwareVersionService {

    SoftwareVersionResponse get(UUID id);

    SoftwareVersionResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<SoftwareVersionResponse> search(SoftwareVersionSearchCriteria criteria, Pageable pageable);

    List<SoftwareVersionSummary> summaries(LifecycleStatus status);

    SoftwareVersionResponse create(SoftwareVersionCreateRequest request);

    SoftwareVersionResponse update(UUID id, SoftwareVersionUpdateRequest request);

    SoftwareVersionResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<SoftwareVersionResponse> bulkCreate(List<SoftwareVersionCreateRequest> requests);

    BulkResult<SoftwareVersionResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(SoftwareVersionSearchCriteria criteria, Writer writer);

    PageResponse<SoftwareVersionResponse> findByImageId(UUID imageId, Pageable pageable);
}
