package com.uniti.cil.itta.inventory.compliance.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.service.PolicyDocumentService;
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
@RequestMapping("/api/v1/policy-documents")
@Tag(name = "Policy Document", description = "Compliance and Audit - Policy Document inventory")
public class PolicyDocumentController {

    private final PolicyDocumentService service;

    public PolicyDocumentController(PolicyDocumentService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Policy Document records")
    public PageResponse<PolicyDocumentResponse> search(PolicyDocumentSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Policy Document records")
    public List<PolicyDocumentSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Policy Document by id")
    public PolicyDocumentResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Policy Document by business code")
    public PolicyDocumentResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Policy Document code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Policy Document")
    public ResponseEntity<PolicyDocumentResponse> create(@Valid @RequestBody PolicyDocumentCreateRequest request) {
        PolicyDocumentResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/policy-documents/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Policy Document")
    public PolicyDocumentResponse update(@PathVariable UUID id, @Valid @RequestBody PolicyDocumentUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Policy Document")
    public PolicyDocumentResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Policy Document")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Policy Document records")
    public BulkResult<PolicyDocumentResponse> bulkCreate(@Valid @RequestBody List<@Valid PolicyDocumentCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Policy Document records")
    public BulkResult<PolicyDocumentResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Policy Document records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Policy Document records as CSV")
    public void export(PolicyDocumentSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"policy-documents.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }
}
