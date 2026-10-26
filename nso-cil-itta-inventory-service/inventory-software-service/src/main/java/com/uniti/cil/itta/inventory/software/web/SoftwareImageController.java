package com.uniti.cil.itta.inventory.software.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.service.SoftwareImageService;
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
@RequestMapping("/api/v1/software-images")
@Tag(name = "Software Image", description = "Software and Licensing - Software Image inventory")
public class SoftwareImageController {

    private final SoftwareImageService service;

    public SoftwareImageController(SoftwareImageService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Software Image records")
    public PageResponse<SoftwareImageResponse> search(SoftwareImageSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Software Image records")
    public List<SoftwareImageSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Software Image by id")
    public SoftwareImageResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Software Image by business code")
    public SoftwareImageResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Software Image code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Software Image")
    public ResponseEntity<SoftwareImageResponse> create(@Valid @RequestBody SoftwareImageCreateRequest request) {
        SoftwareImageResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/software-images/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Software Image")
    public SoftwareImageResponse update(@PathVariable UUID id, @Valid @RequestBody SoftwareImageUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Software Image")
    public SoftwareImageResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Software Image")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Software Image records")
    public BulkResult<SoftwareImageResponse> bulkCreate(@Valid @RequestBody List<@Valid SoftwareImageCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Software Image records")
    public BulkResult<SoftwareImageResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Software Image records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Software Image records as CSV")
    public void export(SoftwareImageSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"software-images.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-vendor-id/{vendorId}")
    @Operation(summary = "List Software Image records by Vendor Id")
    public PageResponse<SoftwareImageResponse> byVendorId(@PathVariable UUID vendorId, Pageable pageable) {
        return service.findByVendorId(vendorId, pageable);
    }
}
