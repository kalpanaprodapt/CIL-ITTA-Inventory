package com.uniti.cil.itta.inventory.integration.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.service.SyncMappingService;
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
@RequestMapping("/api/v1/sync-mappings")
@Tag(name = "Sync Mapping", description = "Integration Layer - Sync Mapping inventory")
public class SyncMappingController {

    private final SyncMappingService service;

    public SyncMappingController(SyncMappingService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Sync Mapping records")
    public PageResponse<SyncMappingResponse> search(SyncMappingSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Sync Mapping records")
    public List<SyncMappingSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Sync Mapping by id")
    public SyncMappingResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Sync Mapping by business code")
    public SyncMappingResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Sync Mapping code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Sync Mapping")
    public ResponseEntity<SyncMappingResponse> create(@Valid @RequestBody SyncMappingCreateRequest request) {
        SyncMappingResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/sync-mappings/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Sync Mapping")
    public SyncMappingResponse update(@PathVariable UUID id, @Valid @RequestBody SyncMappingUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Sync Mapping")
    public SyncMappingResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Sync Mapping")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Sync Mapping records")
    public BulkResult<SyncMappingResponse> bulkCreate(@Valid @RequestBody List<@Valid SyncMappingCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Sync Mapping records")
    public BulkResult<SyncMappingResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Sync Mapping records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Sync Mapping records as CSV")
    public void export(SyncMappingSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"sync-mappings.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-job-id/{jobId}")
    @Operation(summary = "List Sync Mapping records by Job Id")
    public PageResponse<SyncMappingResponse> byJobId(@PathVariable UUID jobId, Pageable pageable) {
        return service.findByJobId(jobId, pageable);
    }
}
