package com.uniti.cil.itta.inventory.mpls.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Qos Policy records. */
public interface QosPolicyService {

    QosPolicyResponse get(UUID id);

    QosPolicyResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<QosPolicyResponse> search(QosPolicySearchCriteria criteria, Pageable pageable);

    List<QosPolicySummary> summaries(LifecycleStatus status);

    QosPolicyResponse create(QosPolicyCreateRequest request);

    QosPolicyResponse update(UUID id, QosPolicyUpdateRequest request);

    QosPolicyResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<QosPolicyResponse> bulkCreate(List<QosPolicyCreateRequest> requests);

    BulkResult<QosPolicyResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(QosPolicySearchCriteria criteria, Writer writer);

}
