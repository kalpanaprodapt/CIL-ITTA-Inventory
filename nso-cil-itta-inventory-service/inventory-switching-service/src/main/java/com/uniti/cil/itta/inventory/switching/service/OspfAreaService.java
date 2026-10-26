package com.uniti.cil.itta.inventory.switching.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Ospf Area records. */
public interface OspfAreaService {

    OspfAreaResponse get(UUID id);

    OspfAreaResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<OspfAreaResponse> search(OspfAreaSearchCriteria criteria, Pageable pageable);

    List<OspfAreaSummary> summaries(LifecycleStatus status);

    OspfAreaResponse create(OspfAreaCreateRequest request);

    OspfAreaResponse update(UUID id, OspfAreaUpdateRequest request);

    OspfAreaResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<OspfAreaResponse> bulkCreate(List<OspfAreaCreateRequest> requests);

    BulkResult<OspfAreaResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(OspfAreaSearchCriteria criteria, Writer writer);

}
