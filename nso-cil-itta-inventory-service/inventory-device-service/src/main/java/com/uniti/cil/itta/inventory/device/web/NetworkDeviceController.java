package com.uniti.cil.itta.inventory.device.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.service.NetworkDeviceService;
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
@RequestMapping("/api/v1/network-devices")
@Tag(name = "Network Device", description = "Network Device - Network Device inventory")
public class NetworkDeviceController {

    private final NetworkDeviceService service;

    public NetworkDeviceController(NetworkDeviceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Network Device records")
    public PageResponse<NetworkDeviceResponse> search(NetworkDeviceSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Network Device records")
    public List<NetworkDeviceSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Network Device by id")
    public NetworkDeviceResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Network Device by business code")
    public NetworkDeviceResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Network Device code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Network Device")
    public ResponseEntity<NetworkDeviceResponse> create(@Valid @RequestBody NetworkDeviceCreateRequest request) {
        NetworkDeviceResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/network-devices/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Network Device")
    public NetworkDeviceResponse update(@PathVariable UUID id, @Valid @RequestBody NetworkDeviceUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Network Device")
    public NetworkDeviceResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Network Device")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Network Device records")
    public BulkResult<NetworkDeviceResponse> bulkCreate(@Valid @RequestBody List<@Valid NetworkDeviceCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Network Device records")
    public BulkResult<NetworkDeviceResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Network Device records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Network Device records as CSV")
    public void export(NetworkDeviceSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"network-devices.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-model-id/{modelId}")
    @Operation(summary = "List Network Device records by Model Id")
    public PageResponse<NetworkDeviceResponse> byModelId(@PathVariable UUID modelId, Pageable pageable) {
        return service.findByModelId(modelId, pageable);
    }

    @GetMapping("/by-rack-unit-id/{rackUnitId}")
    @Operation(summary = "List Network Device records by Rack Unit Id")
    public PageResponse<NetworkDeviceResponse> byRackUnitId(@PathVariable UUID rackUnitId, Pageable pageable) {
        return service.findByRackUnitId(rackUnitId, pageable);
    }
}
