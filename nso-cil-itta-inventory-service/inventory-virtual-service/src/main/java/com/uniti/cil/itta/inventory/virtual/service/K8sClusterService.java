package com.uniti.cil.itta.inventory.virtual.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for K8s Cluster records. */
public interface K8sClusterService {

    K8sClusterResponse get(UUID id);

    K8sClusterResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<K8sClusterResponse> search(K8sClusterSearchCriteria criteria, Pageable pageable);

    List<K8sClusterSummary> summaries(LifecycleStatus status);

    K8sClusterResponse create(K8sClusterCreateRequest request);

    K8sClusterResponse update(UUID id, K8sClusterUpdateRequest request);

    K8sClusterResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<K8sClusterResponse> bulkCreate(List<K8sClusterCreateRequest> requests);

    BulkResult<K8sClusterResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(K8sClusterSearchCriteria criteria, Writer writer);

    PageResponse<K8sClusterResponse> findByCloudAccountId(UUID cloudAccountId, Pageable pageable);
}
