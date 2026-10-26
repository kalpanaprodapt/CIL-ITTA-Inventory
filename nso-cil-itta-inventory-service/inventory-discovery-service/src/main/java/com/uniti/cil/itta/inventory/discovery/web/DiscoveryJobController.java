package com.uniti.cil.itta.inventory.discovery.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.service.DiscoveryJobService;
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
@RequestMapping("/api/v1/discovery-jobs")
@Tag(name = "Discovery Job", description = "Discovery and Reconciliation - Discovery Job inventory")
public class DiscoveryJobController {

    private final DiscoveryJobService service;

    public DiscoveryJobController(DiscoveryJobService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Discovery Job records")
    public PageResponse<DiscoveryJobResponse> search(DiscoveryJobSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Discovery Job records")
    public List<DiscoveryJobSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Discovery Job by id")
    public DiscoveryJobResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Discovery Job by business code")
    public DiscoveryJobResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Discovery Job code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Discovery Job")
    public ResponseEntity<DiscoveryJobResponse> create(@Valid @RequestBody DiscoveryJobCreateRequest request) {
        DiscoveryJobResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/discovery-jobs/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Discovery Job")
    public DiscoveryJobResponse update(@PathVariable UUID id, @Valid @RequestBody DiscoveryJobUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Discovery Job")
    public DiscoveryJobResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Discovery Job")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Discovery Job records")
    public BulkResult<DiscoveryJobResponse> bulkCreate(@Valid @RequestBody List<@Valid DiscoveryJobCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Discovery Job records")
    public BulkResult<DiscoveryJobResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Discovery Job records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Discovery Job records as CSV")
    public void export(DiscoveryJobSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"discovery-jobs.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-profile-id/{profileId}")
    @Operation(summary = "List Discovery Job records by Profile Id")
    public PageResponse<DiscoveryJobResponse> byProfileId(@PathVariable UUID profileId, Pageable pageable) {
        return service.findByProfileId(profileId, pageable);
    }
}
