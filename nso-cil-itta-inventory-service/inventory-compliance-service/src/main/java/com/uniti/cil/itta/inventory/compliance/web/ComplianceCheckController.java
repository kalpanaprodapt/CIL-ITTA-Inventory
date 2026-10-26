package com.uniti.cil.itta.inventory.compliance.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.service.ComplianceCheckService;
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
@RequestMapping("/api/v1/compliance-checks")
@Tag(name = "Compliance Check", description = "Compliance and Audit - Compliance Check inventory")
public class ComplianceCheckController {

    private final ComplianceCheckService service;

    public ComplianceCheckController(ComplianceCheckService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Compliance Check records")
    public PageResponse<ComplianceCheckResponse> search(ComplianceCheckSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Compliance Check records")
    public List<ComplianceCheckSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Compliance Check by id")
    public ComplianceCheckResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Compliance Check by business code")
    public ComplianceCheckResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Compliance Check code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Compliance Check")
    public ResponseEntity<ComplianceCheckResponse> create(@Valid @RequestBody ComplianceCheckCreateRequest request) {
        ComplianceCheckResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/compliance-checks/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Compliance Check")
    public ComplianceCheckResponse update(@PathVariable UUID id, @Valid @RequestBody ComplianceCheckUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Compliance Check")
    public ComplianceCheckResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Compliance Check")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Compliance Check records")
    public BulkResult<ComplianceCheckResponse> bulkCreate(@Valid @RequestBody List<@Valid ComplianceCheckCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Compliance Check records")
    public BulkResult<ComplianceCheckResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Compliance Check records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Compliance Check records as CSV")
    public void export(ComplianceCheckSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"compliance-checks.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-rule-id/{ruleId}")
    @Operation(summary = "List Compliance Check records by Rule Id")
    public PageResponse<ComplianceCheckResponse> byRuleId(@PathVariable UUID ruleId, Pageable pageable) {
        return service.findByRuleId(ruleId, pageable);
    }
}
