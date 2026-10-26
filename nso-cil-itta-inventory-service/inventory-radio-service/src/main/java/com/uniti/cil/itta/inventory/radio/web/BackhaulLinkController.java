package com.uniti.cil.itta.inventory.radio.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.service.BackhaulLinkService;
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
@RequestMapping("/api/v1/backhaul-links")
@Tag(name = "Backhaul Link", description = "Radio and Mobile - Backhaul Link inventory")
public class BackhaulLinkController {

    private final BackhaulLinkService service;

    public BackhaulLinkController(BackhaulLinkService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Backhaul Link records")
    public PageResponse<BackhaulLinkResponse> search(BackhaulLinkSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Backhaul Link records")
    public List<BackhaulLinkSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Backhaul Link by id")
    public BackhaulLinkResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Backhaul Link by business code")
    public BackhaulLinkResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Backhaul Link code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Backhaul Link")
    public ResponseEntity<BackhaulLinkResponse> create(@Valid @RequestBody BackhaulLinkCreateRequest request) {
        BackhaulLinkResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/backhaul-links/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Backhaul Link")
    public BackhaulLinkResponse update(@PathVariable UUID id, @Valid @RequestBody BackhaulLinkUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Backhaul Link")
    public BackhaulLinkResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Backhaul Link")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Backhaul Link records")
    public BulkResult<BackhaulLinkResponse> bulkCreate(@Valid @RequestBody List<@Valid BackhaulLinkCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Backhaul Link records")
    public BulkResult<BackhaulLinkResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Backhaul Link records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Backhaul Link records as CSV")
    public void export(BackhaulLinkSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"backhaul-links.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-base-station-id/{baseStationId}")
    @Operation(summary = "List Backhaul Link records by Base Station Id")
    public PageResponse<BackhaulLinkResponse> byBaseStationId(@PathVariable UUID baseStationId, Pageable pageable) {
        return service.findByBaseStationId(baseStationId, pageable);
    }
}
