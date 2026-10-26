package com.uniti.cil.itta.inventory.virtual.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Cloud Account records. */
public interface CloudAccountService {

    CloudAccountResponse get(UUID id);

    CloudAccountResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CloudAccountResponse> search(CloudAccountSearchCriteria criteria, Pageable pageable);

    List<CloudAccountSummary> summaries(LifecycleStatus status);

    CloudAccountResponse create(CloudAccountCreateRequest request);

    CloudAccountResponse update(UUID id, CloudAccountUpdateRequest request);

    CloudAccountResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CloudAccountResponse> bulkCreate(List<CloudAccountCreateRequest> requests);

    BulkResult<CloudAccountResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CloudAccountSearchCriteria criteria, Writer writer);

}
