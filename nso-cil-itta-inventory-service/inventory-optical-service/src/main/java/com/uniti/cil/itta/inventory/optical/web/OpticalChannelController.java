package com.uniti.cil.itta.inventory.optical.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.service.OpticalChannelService;
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
@RequestMapping("/api/v1/optical-channels")
@Tag(name = "Optical Channel", description = "Optical - Optical Channel inventory")
public class OpticalChannelController {

    private final OpticalChannelService service;

    public OpticalChannelController(OpticalChannelService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Optical Channel records")
    public PageResponse<OpticalChannelResponse> search(OpticalChannelSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Optical Channel records")
    public List<OpticalChannelSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Optical Channel by id")
    public OpticalChannelResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Optical Channel by business code")
    public OpticalChannelResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Optical Channel code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Optical Channel")
    public ResponseEntity<OpticalChannelResponse> create(@Valid @RequestBody OpticalChannelCreateRequest request) {
        OpticalChannelResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/optical-channels/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Optical Channel")
    public OpticalChannelResponse update(@PathVariable UUID id, @Valid @RequestBody OpticalChannelUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Optical Channel")
    public OpticalChannelResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Optical Channel")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Optical Channel records")
    public BulkResult<OpticalChannelResponse> bulkCreate(@Valid @RequestBody List<@Valid OpticalChannelCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Optical Channel records")
    public BulkResult<OpticalChannelResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Optical Channel records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Optical Channel records as CSV")
    public void export(OpticalChannelSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"optical-channels.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-system-id/{systemId}")
    @Operation(summary = "List Optical Channel records by System Id")
    public PageResponse<OpticalChannelResponse> bySystemId(@PathVariable UUID systemId, Pageable pageable) {
        return service.findBySystemId(systemId, pageable);
    }
}
