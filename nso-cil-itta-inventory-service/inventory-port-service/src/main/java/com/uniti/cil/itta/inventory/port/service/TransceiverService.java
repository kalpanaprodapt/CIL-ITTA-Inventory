package com.uniti.cil.itta.inventory.port.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Transceiver records. */
public interface TransceiverService {

    TransceiverResponse get(UUID id);

    TransceiverResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<TransceiverResponse> search(TransceiverSearchCriteria criteria, Pageable pageable);

    List<TransceiverSummary> summaries(LifecycleStatus status);

    TransceiverResponse create(TransceiverCreateRequest request);

    TransceiverResponse update(UUID id, TransceiverUpdateRequest request);

    TransceiverResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<TransceiverResponse> bulkCreate(List<TransceiverCreateRequest> requests);

    BulkResult<TransceiverResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(TransceiverSearchCriteria criteria, Writer writer);

    PageResponse<TransceiverResponse> findByPortId(UUID portId, Pageable pageable);
}
