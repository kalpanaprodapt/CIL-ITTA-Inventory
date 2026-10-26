package com.uniti.cil.itta.inventory.optical.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Amplifier records. */
public interface AmplifierService {

    AmplifierResponse get(UUID id);

    AmplifierResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<AmplifierResponse> search(AmplifierSearchCriteria criteria, Pageable pageable);

    List<AmplifierSummary> summaries(LifecycleStatus status);

    AmplifierResponse create(AmplifierCreateRequest request);

    AmplifierResponse update(UUID id, AmplifierUpdateRequest request);

    AmplifierResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<AmplifierResponse> bulkCreate(List<AmplifierCreateRequest> requests);

    BulkResult<AmplifierResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(AmplifierSearchCriteria criteria, Writer writer);

    PageResponse<AmplifierResponse> findBySystemId(UUID systemId, Pageable pageable);
}
