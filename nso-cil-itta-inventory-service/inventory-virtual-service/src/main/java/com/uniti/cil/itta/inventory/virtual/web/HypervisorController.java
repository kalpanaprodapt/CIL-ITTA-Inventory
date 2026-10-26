package com.uniti.cil.itta.inventory.virtual.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.service.HypervisorService;
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
@RequestMapping("/api/v1/hypervisors")
@Tag(name = "Hypervisor", description = "Virtual and Cloud - Hypervisor inventory")
public class HypervisorController {

    private final HypervisorService service;

    public HypervisorController(HypervisorService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Hypervisor records")
    public PageResponse<HypervisorResponse> search(HypervisorSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Hypervisor records")
    public List<HypervisorSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Hypervisor by id")
    public HypervisorResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Hypervisor by business code")
    public HypervisorResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Hypervisor code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Hypervisor")
    public ResponseEntity<HypervisorResponse> create(@Valid @RequestBody HypervisorCreateRequest request) {
        HypervisorResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/hypervisors/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Hypervisor")
    public HypervisorResponse update(@PathVariable UUID id, @Valid @RequestBody HypervisorUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Hypervisor")
    public HypervisorResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Hypervisor")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Hypervisor records")
    public BulkResult<HypervisorResponse> bulkCreate(@Valid @RequestBody List<@Valid HypervisorCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Hypervisor records")
    public BulkResult<HypervisorResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Hypervisor records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Hypervisor records as CSV")
    public void export(HypervisorSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"hypervisors.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-host-device-id/{hostDeviceId}")
    @Operation(summary = "List Hypervisor records by Host Device Id")
    public PageResponse<HypervisorResponse> byHostDeviceId(@PathVariable UUID hostDeviceId, Pageable pageable) {
        return service.findByHostDeviceId(hostDeviceId, pageable);
    }
}
