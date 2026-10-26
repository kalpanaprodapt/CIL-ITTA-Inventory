package com.uniti.cil.itta.inventory.discovery.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.service.ReconciliationResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reconciliation-results")
@Tag(name = "Reconciliation Result", description = "Discovery and Reconciliation - Reconciliation Result inventory")
public class ReconciliationResultController {

    private final ReconciliationResultService service;

    public ReconciliationResultController(ReconciliationResultService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Reconciliation Result records")
    public PageResponse<ReconciliationResultResponse> search(ReconciliationResultSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Reconciliation Result records")
    public List<ReconciliationResultSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Reconciliation Result by id")
    public ReconciliationResultResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Reconciliation Result by business code")
    public ReconciliationResultResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Reconciliation Result code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Reconciliation Result")
    public ResponseEntity<ReconciliationResultResponse> create(@Valid @RequestBody ReconciliationResultCreateRequest request) {
        ReconciliationResultResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/reconciliation-results/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Reconciliation Result")
    public ReconciliationResultResponse update(@PathVariable UUID id, @Valid @RequestBody ReconciliationResultUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Reconciliation Result")
    public ReconciliationResultResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Reconciliation Result")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Reconciliation Result records")
    public BulkResult<ReconciliationResultResponse> bulkCreate(@Valid @RequestBody List<@Valid ReconciliationResultCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Reconciliation Result records")
    public BulkResult<ReconciliationResultResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Reconciliation Result records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Reconciliation Result records as CSV")
    public void export(ReconciliationResultSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"reconciliation-results.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-job-id/{jobId}")
    @Operation(summary = "List Reconciliation Result records by Job Id")
    public PageResponse<ReconciliationResultResponse> byJobId(@PathVariable UUID jobId, Pageable pageable) {
        return service.findByJobId(jobId, pageable);
    }

    @GetMapping("/by-rule-id/{ruleId}")
    @Operation(summary = "List Reconciliation Result records by Rule Id")
    public PageResponse<ReconciliationResultResponse> byRuleId(@PathVariable UUID ruleId, Pageable pageable) {
        return service.findByRuleId(ruleId, pageable);
    }
}
