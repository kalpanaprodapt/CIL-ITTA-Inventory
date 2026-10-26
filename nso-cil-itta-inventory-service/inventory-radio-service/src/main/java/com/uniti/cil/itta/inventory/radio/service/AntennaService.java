package com.uniti.cil.itta.inventory.radio.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Antenna records. */
public interface AntennaService {

    AntennaResponse get(UUID id);

    AntennaResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<AntennaResponse> search(AntennaSearchCriteria criteria, Pageable pageable);

    List<AntennaSummary> summaries(LifecycleStatus status);

    AntennaResponse create(AntennaCreateRequest request);

    AntennaResponse update(UUID id, AntennaUpdateRequest request);

    AntennaResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<AntennaResponse> bulkCreate(List<AntennaCreateRequest> requests);

    BulkResult<AntennaResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(AntennaSearchCriteria criteria, Writer writer);

    PageResponse<AntennaResponse> findBySectorId(UUID sectorId, Pageable pageable);
}
