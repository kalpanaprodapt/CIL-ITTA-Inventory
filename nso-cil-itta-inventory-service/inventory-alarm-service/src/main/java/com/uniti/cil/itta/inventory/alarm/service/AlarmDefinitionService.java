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

/** Application service for Alarm Definition records. */
public interface AlarmDefinitionService {

    AlarmDefinitionResponse get(UUID id);

    AlarmDefinitionResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<AlarmDefinitionResponse> search(AlarmDefinitionSearchCriteria criteria, Pageable pageable);

    List<AlarmDefinitionSummary> summaries(LifecycleStatus status);

    AlarmDefinitionResponse create(AlarmDefinitionCreateRequest request);

    AlarmDefinitionResponse update(UUID id, AlarmDefinitionUpdateRequest request);

    AlarmDefinitionResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<AlarmDefinitionResponse> bulkCreate(List<AlarmDefinitionCreateRequest> requests);

    BulkResult<AlarmDefinitionResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(AlarmDefinitionSearchCriteria criteria, Writer writer);

}
