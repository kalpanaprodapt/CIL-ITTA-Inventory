package com.uniti.cil.itta.inventory.compliance.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.service.ComplianceRuleService;
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
@RequestMapping("/api/v1/compliance-rules")
@Tag(name = "Compliance Rule", description = "Compliance and Audit - Compliance Rule inventory")
public class ComplianceRuleController {

    private final ComplianceRuleService service;

    public ComplianceRuleController(ComplianceRuleService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Compliance Rule records")
    public PageResponse<ComplianceRuleResponse> search(ComplianceRuleSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Compliance Rule records")
    public List<ComplianceRuleSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Compliance Rule by id")
    public ComplianceRuleResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Compliance Rule by business code")
    public ComplianceRuleResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Compliance Rule code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Compliance Rule")
    public ResponseEntity<ComplianceRuleResponse> create(@Valid @RequestBody ComplianceRuleCreateRequest request) {
        ComplianceRuleResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/compliance-rules/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Compliance Rule")
    public ComplianceRuleResponse update(@PathVariable UUID id, @Valid @RequestBody ComplianceRuleUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Compliance Rule")
    public ComplianceRuleResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Compliance Rule")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Compliance Rule records")
    public BulkResult<ComplianceRuleResponse> bulkCreate(@Valid @RequestBody List<@Valid ComplianceRuleCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Compliance Rule records")
    public BulkResult<ComplianceRuleResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Compliance Rule records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Compliance Rule records as CSV")
    public void export(ComplianceRuleSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"compliance-rules.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }
}
