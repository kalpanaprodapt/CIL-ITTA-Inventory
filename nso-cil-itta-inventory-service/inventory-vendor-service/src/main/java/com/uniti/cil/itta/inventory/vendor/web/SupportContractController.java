package com.uniti.cil.itta.inventory.vendor.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.service.SupportContractService;
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
@RequestMapping("/api/v1/support-contracts")
@Tag(name = "Support Contract", description = "Vendor and Model - Support Contract inventory")
public class SupportContractController {

    private final SupportContractService service;

    public SupportContractController(SupportContractService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Support Contract records")
    public PageResponse<SupportContractResponse> search(SupportContractSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Support Contract records")
    public List<SupportContractSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Support Contract by id")
    public SupportContractResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Support Contract by business code")
    public SupportContractResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Support Contract code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Support Contract")
    public ResponseEntity<SupportContractResponse> create(@Valid @RequestBody SupportContractCreateRequest request) {
        SupportContractResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/support-contracts/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Support Contract")
    public SupportContractResponse update(@PathVariable UUID id, @Valid @RequestBody SupportContractUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Support Contract")
    public SupportContractResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Support Contract")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Support Contract records")
    public BulkResult<SupportContractResponse> bulkCreate(@Valid @RequestBody List<@Valid SupportContractCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Support Contract records")
    public BulkResult<SupportContractResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Support Contract records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Support Contract records as CSV")
    public void export(SupportContractSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"support-contracts.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-vendor-id/{vendorId}")
    @Operation(summary = "List Support Contract records by Vendor Id")
    public PageResponse<SupportContractResponse> byVendorId(@PathVariable UUID vendorId, Pageable pageable) {
        return service.findByVendorId(vendorId, pageable);
    }
}
