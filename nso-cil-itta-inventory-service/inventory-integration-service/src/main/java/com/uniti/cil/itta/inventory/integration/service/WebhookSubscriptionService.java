package com.uniti.cil.itta.inventory.integration.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Webhook Subscription records. */
public interface WebhookSubscriptionService {

    WebhookSubscriptionResponse get(UUID id);

    WebhookSubscriptionResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<WebhookSubscriptionResponse> search(WebhookSubscriptionSearchCriteria criteria, Pageable pageable);

    List<WebhookSubscriptionSummary> summaries(LifecycleStatus status);

    WebhookSubscriptionResponse create(WebhookSubscriptionCreateRequest request);

    WebhookSubscriptionResponse update(UUID id, WebhookSubscriptionUpdateRequest request);

    WebhookSubscriptionResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<WebhookSubscriptionResponse> bulkCreate(List<WebhookSubscriptionCreateRequest> requests);

    BulkResult<WebhookSubscriptionResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(WebhookSubscriptionSearchCriteria criteria, Writer writer);

}
