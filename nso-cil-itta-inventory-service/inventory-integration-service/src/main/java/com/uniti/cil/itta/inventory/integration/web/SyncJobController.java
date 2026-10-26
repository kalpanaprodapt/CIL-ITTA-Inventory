package com.uniti.cil.itta.inventory.integration.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.service.SyncJobService;
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
@RequestMapping("/api/v1/sync-jobs")
@Tag(name = "Sync Job", description = "Integration Layer - Sync Job inventory")
public class SyncJobController {

    private final SyncJobService service;

    public SyncJobController(SyncJobService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Sync Job records")
    public PageResponse<SyncJobResponse> search(SyncJobSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Sync Job records")
    public List<SyncJobSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Sync Job by id")
    public SyncJobResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Sync Job by business code")
    public SyncJobResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Sync Job code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Sync Job")
    public ResponseEntity<SyncJobResponse> create(@Valid @RequestBody SyncJobCreateRequest request) {
        SyncJobResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/sync-jobs/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Sync Job")
    public SyncJobResponse update(@PathVariable UUID id, @Valid @RequestBody SyncJobUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Sync Job")
    public SyncJobResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Sync Job")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Sync Job records")
    public BulkResult<SyncJobResponse> bulkCreate(@Valid @RequestBody List<@Valid SyncJobCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Sync Job records")
    public BulkResult<SyncJobResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Sync Job records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Sync Job records as CSV")
    public void export(SyncJobSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"sync-jobs.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-endpoint-id/{endpointId}")
    @Operation(summary = "List Sync Job records by Endpoint Id")
    public PageResponse<SyncJobResponse> byEndpointId(@PathVariable UUID endpointId, Pageable pageable) {
        return service.findByEndpointId(endpointId, pageable);
    }
}
