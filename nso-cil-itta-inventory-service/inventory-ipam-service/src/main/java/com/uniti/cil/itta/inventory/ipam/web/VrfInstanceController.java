package com.uniti.cil.itta.inventory.ipam.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.service.VrfInstanceService;
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
@RequestMapping("/api/v1/vrf-instances")
@Tag(name = "Vrf Instance", description = "IP Management - Vrf Instance inventory")
public class VrfInstanceController {

    private final VrfInstanceService service;

    public VrfInstanceController(VrfInstanceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Vrf Instance records")
    public PageResponse<VrfInstanceResponse> search(VrfInstanceSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Vrf Instance records")
    public List<VrfInstanceSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Vrf Instance by id")
    public VrfInstanceResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Vrf Instance by business code")
    public VrfInstanceResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Vrf Instance code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Vrf Instance")
    public ResponseEntity<VrfInstanceResponse> create(@Valid @RequestBody VrfInstanceCreateRequest request) {
        VrfInstanceResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/vrf-instances/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Vrf Instance")
    public VrfInstanceResponse update(@PathVariable UUID id, @Valid @RequestBody VrfInstanceUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Vrf Instance")
    public VrfInstanceResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Vrf Instance")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Vrf Instance records")
    public BulkResult<VrfInstanceResponse> bulkCreate(@Valid @RequestBody List<@Valid VrfInstanceCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Vrf Instance records")
    public BulkResult<VrfInstanceResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Vrf Instance records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Vrf Instance records as CSV")
    public void export(VrfInstanceSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"vrf-instances.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-device-id/{deviceId}")
    @Operation(summary = "List Vrf Instance records by Device Id")
    public PageResponse<VrfInstanceResponse> byDeviceId(@PathVariable UUID deviceId, Pageable pageable) {
        return service.findByDeviceId(deviceId, pageable);
    }
}
