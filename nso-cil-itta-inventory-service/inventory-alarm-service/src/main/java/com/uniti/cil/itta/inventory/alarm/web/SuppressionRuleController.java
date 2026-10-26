package com.uniti.cil.itta.inventory.alarm.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.service.SuppressionRuleService;
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
@RequestMapping("/api/v1/suppression-rules")
@Tag(name = "Suppression Rule", description = "Alarms and Events - Suppression Rule inventory")
public class SuppressionRuleController {

    private final SuppressionRuleService service;

    public SuppressionRuleController(SuppressionRuleService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Suppression Rule records")
    public PageResponse<SuppressionRuleResponse> search(SuppressionRuleSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Suppression Rule records")
    public List<SuppressionRuleSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Suppression Rule by id")
    public SuppressionRuleResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Suppression Rule by business code")
    public SuppressionRuleResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Suppression Rule code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Suppression Rule")
    public ResponseEntity<SuppressionRuleResponse> create(@Valid @RequestBody SuppressionRuleCreateRequest request) {
        SuppressionRuleResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/suppression-rules/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Suppression Rule")
    public SuppressionRuleResponse update(@PathVariable UUID id, @Valid @RequestBody SuppressionRuleUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Suppression Rule")
    public SuppressionRuleResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Suppression Rule")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Suppression Rule records")
    public BulkResult<SuppressionRuleResponse> bulkCreate(@Valid @RequestBody List<@Valid SuppressionRuleCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Suppression Rule records")
    public BulkResult<SuppressionRuleResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Suppression Rule records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Suppression Rule records as CSV")
    public void export(SuppressionRuleSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"suppression-rules.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }
}
