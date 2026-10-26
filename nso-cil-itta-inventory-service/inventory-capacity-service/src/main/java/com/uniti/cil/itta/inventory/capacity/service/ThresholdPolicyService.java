package com.uniti.cil.itta.inventory.capacity.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Threshold Policy records. */
public interface ThresholdPolicyService {

    ThresholdPolicyResponse get(UUID id);

    ThresholdPolicyResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ThresholdPolicyResponse> search(ThresholdPolicySearchCriteria criteria, Pageable pageable);

    List<ThresholdPolicySummary> summaries(LifecycleStatus status);

    ThresholdPolicyResponse create(ThresholdPolicyCreateRequest request);

    ThresholdPolicyResponse update(UUID id, ThresholdPolicyUpdateRequest request);

    ThresholdPolicyResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ThresholdPolicyResponse> bulkCreate(List<ThresholdPolicyCreateRequest> requests);

    BulkResult<ThresholdPolicyResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ThresholdPolicySearchCriteria criteria, Writer writer);

}
