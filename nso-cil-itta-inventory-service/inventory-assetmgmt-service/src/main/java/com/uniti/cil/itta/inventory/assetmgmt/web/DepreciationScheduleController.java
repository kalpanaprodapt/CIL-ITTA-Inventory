package com.uniti.cil.itta.inventory.assetmgmt.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.service.DepreciationScheduleService;
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
@RequestMapping("/api/v1/depreciation-schedules")
@Tag(name = "Depreciation Schedule", description = "Asset and Finance - Depreciation Schedule inventory")
public class DepreciationScheduleController {

    private final DepreciationScheduleService service;

    public DepreciationScheduleController(DepreciationScheduleService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Depreciation Schedule records")
    public PageResponse<DepreciationScheduleResponse> search(DepreciationScheduleSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Depreciation Schedule records")
    public List<DepreciationScheduleSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Depreciation Schedule by id")
    public DepreciationScheduleResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Depreciation Schedule by business code")
    public DepreciationScheduleResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Depreciation Schedule code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Depreciation Schedule")
    public ResponseEntity<DepreciationScheduleResponse> create(@Valid @RequestBody DepreciationScheduleCreateRequest request) {
        DepreciationScheduleResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/depreciation-schedules/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Depreciation Schedule")
    public DepreciationScheduleResponse update(@PathVariable UUID id, @Valid @RequestBody DepreciationScheduleUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Depreciation Schedule")
    public DepreciationScheduleResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Depreciation Schedule")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Depreciation Schedule records")
    public BulkResult<DepreciationScheduleResponse> bulkCreate(@Valid @RequestBody List<@Valid DepreciationScheduleCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Depreciation Schedule records")
    public BulkResult<DepreciationScheduleResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Depreciation Schedule records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Depreciation Schedule records as CSV")
    public void export(DepreciationScheduleSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"depreciation-schedules.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-asset-id/{assetId}")
    @Operation(summary = "List Depreciation Schedule records by Asset Id")
    public PageResponse<DepreciationScheduleResponse> byAssetId(@PathVariable UUID assetId, Pageable pageable) {
        return service.findByAssetId(assetId, pageable);
    }
}
