package com.uniti.cil.itta.inventory.discovery.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.service.ReconciliationRuleService;
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
@RequestMapping("/api/v1/reconciliation-rules")
@Tag(name = "Reconciliation Rule", description = "Discovery and Reconciliation - Reconciliation Rule inventory")
public class ReconciliationRuleController {

    private final ReconciliationRuleService service;

    public ReconciliationRuleController(ReconciliationRuleService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Reconciliation Rule records")
    public PageResponse<ReconciliationRuleResponse> search(ReconciliationRuleSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Reconciliation Rule records")
    public List<ReconciliationRuleSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Reconciliation Rule by id")
    public ReconciliationRuleResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Reconciliation Rule by business code")
    public ReconciliationRuleResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Reconciliation Rule code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Reconciliation Rule")
    public ResponseEntity<ReconciliationRuleResponse> create(@Valid @RequestBody ReconciliationRuleCreateRequest request) {
        ReconciliationRuleResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/reconciliation-rules/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Reconciliation Rule")
    public ReconciliationRuleResponse update(@PathVariable UUID id, @Valid @RequestBody ReconciliationRuleUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Reconciliation Rule")
    public ReconciliationRuleResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Reconciliation Rule")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Reconciliation Rule records")
    public BulkResult<ReconciliationRuleResponse> bulkCreate(@Valid @RequestBody List<@Valid ReconciliationRuleCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Reconciliation Rule records")
    public BulkResult<ReconciliationRuleResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Reconciliation Rule records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Reconciliation Rule records as CSV")
    public void export(ReconciliationRuleSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"reconciliation-rules.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }
}
