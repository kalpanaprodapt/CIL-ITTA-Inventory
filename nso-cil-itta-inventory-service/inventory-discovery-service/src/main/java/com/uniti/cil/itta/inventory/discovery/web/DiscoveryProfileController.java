package com.uniti.cil.itta.inventory.discovery.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.service.DiscoveryProfileService;
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
@RequestMapping("/api/v1/discovery-profiles")
@Tag(name = "Discovery Profile", description = "Discovery and Reconciliation - Discovery Profile inventory")
public class DiscoveryProfileController {

    private final DiscoveryProfileService service;

    public DiscoveryProfileController(DiscoveryProfileService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Discovery Profile records")
    public PageResponse<DiscoveryProfileResponse> search(DiscoveryProfileSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Discovery Profile records")
    public List<DiscoveryProfileSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Discovery Profile by id")
    public DiscoveryProfileResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Discovery Profile by business code")
    public DiscoveryProfileResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Discovery Profile code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Discovery Profile")
    public ResponseEntity<DiscoveryProfileResponse> create(@Valid @RequestBody DiscoveryProfileCreateRequest request) {
        DiscoveryProfileResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/discovery-profiles/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Discovery Profile")
    public DiscoveryProfileResponse update(@PathVariable UUID id, @Valid @RequestBody DiscoveryProfileUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Discovery Profile")
    public DiscoveryProfileResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Discovery Profile")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Discovery Profile records")
    public BulkResult<DiscoveryProfileResponse> bulkCreate(@Valid @RequestBody List<@Valid DiscoveryProfileCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Discovery Profile records")
    public BulkResult<DiscoveryProfileResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Discovery Profile records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Discovery Profile records as CSV")
    public void export(DiscoveryProfileSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"discovery-profiles.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }
}
