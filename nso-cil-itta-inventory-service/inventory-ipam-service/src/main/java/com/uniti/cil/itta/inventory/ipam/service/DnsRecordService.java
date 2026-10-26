package com.uniti.cil.itta.inventory.ipam.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Dns Record records. */
public interface DnsRecordService {

    DnsRecordResponse get(UUID id);

    DnsRecordResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<DnsRecordResponse> search(DnsRecordSearchCriteria criteria, Pageable pageable);

    List<DnsRecordSummary> summaries(LifecycleStatus status);

    DnsRecordResponse create(DnsRecordCreateRequest request);

    DnsRecordResponse update(UUID id, DnsRecordUpdateRequest request);

    DnsRecordResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<DnsRecordResponse> bulkCreate(List<DnsRecordCreateRequest> requests);

    BulkResult<DnsRecordResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(DnsRecordSearchCriteria criteria, Writer writer);

}
