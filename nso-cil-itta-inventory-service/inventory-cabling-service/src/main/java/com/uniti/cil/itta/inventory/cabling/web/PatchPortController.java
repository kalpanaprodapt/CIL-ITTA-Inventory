package com.uniti.cil.itta.inventory.cabling.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.service.PatchPortService;
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
@RequestMapping("/api/v1/patch-ports")
@Tag(name = "Patch Port", description = "Cabling - Patch Port inventory")
public class PatchPortController {

    private final PatchPortService service;

    public PatchPortController(PatchPortService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Patch Port records")
    public PageResponse<PatchPortResponse> search(PatchPortSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Patch Port records")
    public List<PatchPortSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Patch Port by id")
    public PatchPortResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Patch Port by business code")
    public PatchPortResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Patch Port code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Patch Port")
    public ResponseEntity<PatchPortResponse> create(@Valid @RequestBody PatchPortCreateRequest request) {
        PatchPortResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/patch-ports/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Patch Port")
    public PatchPortResponse update(@PathVariable UUID id, @Valid @RequestBody PatchPortUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Patch Port")
    public PatchPortResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Patch Port")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Patch Port records")
    public BulkResult<PatchPortResponse> bulkCreate(@Valid @RequestBody List<@Valid PatchPortCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Patch Port records")
    public BulkResult<PatchPortResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Patch Port records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Patch Port records as CSV")
    public void export(PatchPortSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"patch-ports.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-panel-id/{panelId}")
    @Operation(summary = "List Patch Port records by Panel Id")
    public PageResponse<PatchPortResponse> byPanelId(@PathVariable UUID panelId, Pageable pageable) {
        return service.findByPanelId(panelId, pageable);
    }

    @GetMapping("/by-connected-cable-id/{connectedCableId}")
    @Operation(summary = "List Patch Port records by Connected Cable Id")
    public PageResponse<PatchPortResponse> byConnectedCableId(@PathVariable UUID connectedCableId, Pageable pageable) {
        return service.findByConnectedCableId(connectedCableId, pageable);
    }
}
