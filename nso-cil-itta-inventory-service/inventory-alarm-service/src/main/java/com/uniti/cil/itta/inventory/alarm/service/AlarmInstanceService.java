package com.uniti.cil.itta.inventory.alarm.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Alarm Instance records. */
public interface AlarmInstanceService {

    AlarmInstanceResponse get(UUID id);

    AlarmInstanceResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<AlarmInstanceResponse> search(AlarmInstanceSearchCriteria criteria, Pageable pageable);

    List<AlarmInstanceSummary> summaries(LifecycleStatus status);

    AlarmInstanceResponse create(AlarmInstanceCreateRequest request);

    AlarmInstanceResponse update(UUID id, AlarmInstanceUpdateRequest request);

    AlarmInstanceResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<AlarmInstanceResponse> bulkCreate(List<AlarmInstanceCreateRequest> requests);

    BulkResult<AlarmInstanceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(AlarmInstanceSearchCriteria criteria, Writer writer);

    PageResponse<AlarmInstanceResponse> findByDefinitionId(UUID definitionId, Pageable pageable);
}
