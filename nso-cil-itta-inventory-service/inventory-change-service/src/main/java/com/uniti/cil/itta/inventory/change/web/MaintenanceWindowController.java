package com.uniti.cil.itta.inventory.change.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.service.MaintenanceWindowService;
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
@RequestMapping("/api/v1/maintenance-windows")
@Tag(name = "Maintenance Window", description = "Change and Work Management - Maintenance Window inventory")
public class MaintenanceWindowController {

    private final MaintenanceWindowService service;

    public MaintenanceWindowController(MaintenanceWindowService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Maintenance Window records")
    public PageResponse<MaintenanceWindowResponse> search(MaintenanceWindowSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Maintenance Window records")
    public List<MaintenanceWindowSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Maintenance Window by id")
    public MaintenanceWindowResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Maintenance Window by business code")
    public MaintenanceWindowResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Maintenance Window code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Maintenance Window")
    public ResponseEntity<MaintenanceWindowResponse> create(@Valid @RequestBody MaintenanceWindowCreateRequest request) {
        MaintenanceWindowResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/maintenance-windows/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Maintenance Window")
    public MaintenanceWindowResponse update(@PathVariable UUID id, @Valid @RequestBody MaintenanceWindowUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Maintenance Window")
    public MaintenanceWindowResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Maintenance Window")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Maintenance Window records")
    public BulkResult<MaintenanceWindowResponse> bulkCreate(@Valid @RequestBody List<@Valid MaintenanceWindowCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Maintenance Window records")
    public BulkResult<MaintenanceWindowResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Maintenance Window records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Maintenance Window records as CSV")
    public void export(MaintenanceWindowSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"maintenance-windows.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }
}
