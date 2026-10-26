package com.uniti.cil.itta.inventory.topology.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Link records. */
public interface LinkService {

    LinkResponse get(UUID id);

    LinkResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<LinkResponse> search(LinkSearchCriteria criteria, Pageable pageable);

    List<LinkSummary> summaries(LifecycleStatus status);

    LinkResponse create(LinkCreateRequest request);

    LinkResponse update(UUID id, LinkUpdateRequest request);

    LinkResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<LinkResponse> bulkCreate(List<LinkCreateRequest> requests);

    BulkResult<LinkResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(LinkSearchCriteria criteria, Writer writer);

    PageResponse<LinkResponse> findByAInterfaceId(UUID aInterfaceId, Pageable pageable);

    PageResponse<LinkResponse> findByZInterfaceId(UUID zInterfaceId, Pageable pageable);
}
