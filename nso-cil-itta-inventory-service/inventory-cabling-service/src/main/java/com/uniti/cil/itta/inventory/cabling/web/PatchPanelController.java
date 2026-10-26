package com.uniti.cil.itta.inventory.cabling.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.service.PatchPanelService;
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
@RequestMapping("/api/v1/patch-panels")
@Tag(name = "Patch Panel", description = "Cabling - Patch Panel inventory")
public class PatchPanelController {

    private final PatchPanelService service;

    public PatchPanelController(PatchPanelService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Patch Panel records")
    public PageResponse<PatchPanelResponse> search(PatchPanelSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Patch Panel records")
    public List<PatchPanelSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Patch Panel by id")
    public PatchPanelResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Patch Panel by business code")
    public PatchPanelResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Patch Panel code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Patch Panel")
    public ResponseEntity<PatchPanelResponse> create(@Valid @RequestBody PatchPanelCreateRequest request) {
        PatchPanelResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/patch-panels/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Patch Panel")
    public PatchPanelResponse update(@PathVariable UUID id, @Valid @RequestBody PatchPanelUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Patch Panel")
    public PatchPanelResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Patch Panel")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Patch Panel records")
    public BulkResult<PatchPanelResponse> bulkCreate(@Valid @RequestBody List<@Valid PatchPanelCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Patch Panel records")
    public BulkResult<PatchPanelResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Patch Panel records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Patch Panel records as CSV")
    public void export(PatchPanelSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"patch-panels.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-room-id/{roomId}")
    @Operation(summary = "List Patch Panel records by Room Id")
    public PageResponse<PatchPanelResponse> byRoomId(@PathVariable UUID roomId, Pageable pageable) {
        return service.findByRoomId(roomId, pageable);
    }

    @GetMapping("/by-rack-id/{rackId}")
    @Operation(summary = "List Patch Panel records by Rack Id")
    public PageResponse<PatchPanelResponse> byRackId(@PathVariable UUID rackId, Pageable pageable) {
        return service.findByRackId(rackId, pageable);
    }
}
