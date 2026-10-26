package com.uniti.cil.itta.inventory.vendor.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.service.HardwareModelService;
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
@RequestMapping("/api/v1/hardware-models")
@Tag(name = "Hardware Model", description = "Vendor and Model - Hardware Model inventory")
public class HardwareModelController {

    private final HardwareModelService service;

    public HardwareModelController(HardwareModelService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Hardware Model records")
    public PageResponse<HardwareModelResponse> search(HardwareModelSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Hardware Model records")
    public List<HardwareModelSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Hardware Model by id")
    public HardwareModelResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Hardware Model by business code")
    public HardwareModelResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Hardware Model code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Hardware Model")
    public ResponseEntity<HardwareModelResponse> create(@Valid @RequestBody HardwareModelCreateRequest request) {
        HardwareModelResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/hardware-models/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Hardware Model")
    public HardwareModelResponse update(@PathVariable UUID id, @Valid @RequestBody HardwareModelUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Hardware Model")
    public HardwareModelResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Hardware Model")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Hardware Model records")
    public BulkResult<HardwareModelResponse> bulkCreate(@Valid @RequestBody List<@Valid HardwareModelCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Hardware Model records")
    public BulkResult<HardwareModelResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Hardware Model records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Hardware Model records as CSV")
    public void export(HardwareModelSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"hardware-models.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-vendor-id/{vendorId}")
    @Operation(summary = "List Hardware Model records by Vendor Id")
    public PageResponse<HardwareModelResponse> byVendorId(@PathVariable UUID vendorId, Pageable pageable) {
        return service.findByVendorId(vendorId, pageable);
    }
}
