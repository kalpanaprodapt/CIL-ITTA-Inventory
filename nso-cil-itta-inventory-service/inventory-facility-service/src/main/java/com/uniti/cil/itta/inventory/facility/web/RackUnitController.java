package com.uniti.cil.itta.inventory.facility.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.service.RackUnitService;
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
@RequestMapping("/api/v1/rack-units")
@Tag(name = "Rack Unit", description = "Facility - Rack Unit inventory")
public class RackUnitController {

    private final RackUnitService service;

    public RackUnitController(RackUnitService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Rack Unit records")
    public PageResponse<RackUnitResponse> search(RackUnitSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Rack Unit records")
    public List<RackUnitSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Rack Unit by id")
    public RackUnitResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Rack Unit by business code")
    public RackUnitResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Rack Unit code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Rack Unit")
    public ResponseEntity<RackUnitResponse> create(@Valid @RequestBody RackUnitCreateRequest request) {
        RackUnitResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/rack-units/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Rack Unit")
    public RackUnitResponse update(@PathVariable UUID id, @Valid @RequestBody RackUnitUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Rack Unit")
    public RackUnitResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Rack Unit")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Rack Unit records")
    public BulkResult<RackUnitResponse> bulkCreate(@Valid @RequestBody List<@Valid RackUnitCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Rack Unit records")
    public BulkResult<RackUnitResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Rack Unit records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Rack Unit records as CSV")
    public void export(RackUnitSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"rack-units.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-rack-id/{rackId}")
    @Operation(summary = "List Rack Unit records by Rack Id")
    public PageResponse<RackUnitResponse> byRackId(@PathVariable UUID rackId, Pageable pageable) {
        return service.findByRackId(rackId, pageable);
    }

    @GetMapping("/by-mounted-device-id/{mountedDeviceId}")
    @Operation(summary = "List Rack Unit records by Mounted Device Id")
    public PageResponse<RackUnitResponse> byMountedDeviceId(@PathVariable UUID mountedDeviceId, Pageable pageable) {
        return service.findByMountedDeviceId(mountedDeviceId, pageable);
    }
}
