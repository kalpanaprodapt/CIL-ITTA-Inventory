package com.uniti.cil.itta.inventory.software.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.service.LicenseKeyService;
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
@RequestMapping("/api/v1/license-keies")
@Tag(name = "License Key", description = "Software and Licensing - License Key inventory")
public class LicenseKeyController {

    private final LicenseKeyService service;

    public LicenseKeyController(LicenseKeyService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search License Key records")
    public PageResponse<LicenseKeyResponse> search(LicenseKeySearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of License Key records")
    public List<LicenseKeySummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a License Key by id")
    public LicenseKeyResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a License Key by business code")
    public LicenseKeyResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a License Key code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a License Key")
    public ResponseEntity<LicenseKeyResponse> create(@Valid @RequestBody LicenseKeyCreateRequest request) {
        LicenseKeyResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/license-keies/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a License Key")
    public LicenseKeyResponse update(@PathVariable UUID id, @Valid @RequestBody LicenseKeyUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a License Key")
    public LicenseKeyResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a License Key")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many License Key records")
    public BulkResult<LicenseKeyResponse> bulkCreate(@Valid @RequestBody List<@Valid LicenseKeyCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many License Key records")
    public BulkResult<LicenseKeyResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of License Key records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export License Key records as CSV")
    public void export(LicenseKeySearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"license-keies.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-pool-id/{poolId}")
    @Operation(summary = "List License Key records by Pool Id")
    public PageResponse<LicenseKeyResponse> byPoolId(@PathVariable UUID poolId, Pageable pageable) {
        return service.findByPoolId(poolId, pageable);
    }

    @GetMapping("/by-assigned-device-id/{assignedDeviceId}")
    @Operation(summary = "List License Key records by Assigned Device Id")
    public PageResponse<LicenseKeyResponse> byAssignedDeviceId(@PathVariable UUID assignedDeviceId, Pageable pageable) {
        return service.findByAssignedDeviceId(assignedDeviceId, pageable);
    }
}
