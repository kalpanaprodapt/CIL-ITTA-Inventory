package com.uniti.cil.itta.inventory.software.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.service.LicensePoolService;
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
@RequestMapping("/api/v1/license-pools")
@Tag(name = "License Pool", description = "Software and Licensing - License Pool inventory")
public class LicensePoolController {

    private final LicensePoolService service;

    public LicensePoolController(LicensePoolService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search License Pool records")
    public PageResponse<LicensePoolResponse> search(LicensePoolSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of License Pool records")
    public List<LicensePoolSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a License Pool by id")
    public LicensePoolResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a License Pool by business code")
    public LicensePoolResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a License Pool code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a License Pool")
    public ResponseEntity<LicensePoolResponse> create(@Valid @RequestBody LicensePoolCreateRequest request) {
        LicensePoolResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/license-pools/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a License Pool")
    public LicensePoolResponse update(@PathVariable UUID id, @Valid @RequestBody LicensePoolUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a License Pool")
    public LicensePoolResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a License Pool")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many License Pool records")
    public BulkResult<LicensePoolResponse> bulkCreate(@Valid @RequestBody List<@Valid LicensePoolCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many License Pool records")
    public BulkResult<LicensePoolResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of License Pool records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export License Pool records as CSV")
    public void export(LicensePoolSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"license-pools.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-vendor-id/{vendorId}")
    @Operation(summary = "List License Pool records by Vendor Id")
    public PageResponse<LicensePoolResponse> byVendorId(@PathVariable UUID vendorId, Pageable pageable) {
        return service.findByVendorId(vendorId, pageable);
    }
}
