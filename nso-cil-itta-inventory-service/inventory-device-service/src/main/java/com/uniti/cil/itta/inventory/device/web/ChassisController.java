package com.uniti.cil.itta.inventory.device.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.service.ChassisService;
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
@RequestMapping("/api/v1/chassises")
@Tag(name = "Chassis", description = "Network Device - Chassis inventory")
public class ChassisController {

    private final ChassisService service;

    public ChassisController(ChassisService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Chassis records")
    public PageResponse<ChassisResponse> search(ChassisSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Chassis records")
    public List<ChassisSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Chassis by id")
    public ChassisResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Chassis by business code")
    public ChassisResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Chassis code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Chassis")
    public ResponseEntity<ChassisResponse> create(@Valid @RequestBody ChassisCreateRequest request) {
        ChassisResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/chassises/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Chassis")
    public ChassisResponse update(@PathVariable UUID id, @Valid @RequestBody ChassisUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Chassis")
    public ChassisResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Chassis")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Chassis records")
    public BulkResult<ChassisResponse> bulkCreate(@Valid @RequestBody List<@Valid ChassisCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Chassis records")
    public BulkResult<ChassisResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Chassis records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Chassis records as CSV")
    public void export(ChassisSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"chassises.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-device-id/{deviceId}")
    @Operation(summary = "List Chassis records by Device Id")
    public PageResponse<ChassisResponse> byDeviceId(@PathVariable UUID deviceId, Pageable pageable) {
        return service.findByDeviceId(deviceId, pageable);
    }
}
