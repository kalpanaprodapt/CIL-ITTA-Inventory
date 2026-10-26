package com.uniti.cil.itta.inventory.port.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.service.LagGroupService;
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
@RequestMapping("/api/v1/lag-groups")
@Tag(name = "Lag Group", description = "Port and Interface - Lag Group inventory")
public class LagGroupController {

    private final LagGroupService service;

    public LagGroupController(LagGroupService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Lag Group records")
    public PageResponse<LagGroupResponse> search(LagGroupSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Lag Group records")
    public List<LagGroupSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Lag Group by id")
    public LagGroupResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Lag Group by business code")
    public LagGroupResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Lag Group code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Lag Group")
    public ResponseEntity<LagGroupResponse> create(@Valid @RequestBody LagGroupCreateRequest request) {
        LagGroupResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/lag-groups/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Lag Group")
    public LagGroupResponse update(@PathVariable UUID id, @Valid @RequestBody LagGroupUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Lag Group")
    public LagGroupResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Lag Group")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Lag Group records")
    public BulkResult<LagGroupResponse> bulkCreate(@Valid @RequestBody List<@Valid LagGroupCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Lag Group records")
    public BulkResult<LagGroupResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Lag Group records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Lag Group records as CSV")
    public void export(LagGroupSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"lag-groups.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-device-id/{deviceId}")
    @Operation(summary = "List Lag Group records by Device Id")
    public PageResponse<LagGroupResponse> byDeviceId(@PathVariable UUID deviceId, Pageable pageable) {
        return service.findByDeviceId(deviceId, pageable);
    }
}
