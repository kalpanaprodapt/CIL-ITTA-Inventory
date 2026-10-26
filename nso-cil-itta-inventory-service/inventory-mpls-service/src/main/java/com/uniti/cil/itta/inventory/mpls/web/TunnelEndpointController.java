package com.uniti.cil.itta.inventory.mpls.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.service.TunnelEndpointService;
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
@RequestMapping("/api/v1/tunnel-endpoints")
@Tag(name = "Tunnel Endpoint", description = "MPLS and VPN - Tunnel Endpoint inventory")
public class TunnelEndpointController {

    private final TunnelEndpointService service;

    public TunnelEndpointController(TunnelEndpointService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Tunnel Endpoint records")
    public PageResponse<TunnelEndpointResponse> search(TunnelEndpointSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Tunnel Endpoint records")
    public List<TunnelEndpointSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Tunnel Endpoint by id")
    public TunnelEndpointResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Tunnel Endpoint by business code")
    public TunnelEndpointResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Tunnel Endpoint code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Tunnel Endpoint")
    public ResponseEntity<TunnelEndpointResponse> create(@Valid @RequestBody TunnelEndpointCreateRequest request) {
        TunnelEndpointResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/tunnel-endpoints/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Tunnel Endpoint")
    public TunnelEndpointResponse update(@PathVariable UUID id, @Valid @RequestBody TunnelEndpointUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Tunnel Endpoint")
    public TunnelEndpointResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Tunnel Endpoint")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Tunnel Endpoint records")
    public BulkResult<TunnelEndpointResponse> bulkCreate(@Valid @RequestBody List<@Valid TunnelEndpointCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Tunnel Endpoint records")
    public BulkResult<TunnelEndpointResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Tunnel Endpoint records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Tunnel Endpoint records as CSV")
    public void export(TunnelEndpointSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"tunnel-endpoints.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-device-id/{deviceId}")
    @Operation(summary = "List Tunnel Endpoint records by Device Id")
    public PageResponse<TunnelEndpointResponse> byDeviceId(@PathVariable UUID deviceId, Pageable pageable) {
        return service.findByDeviceId(deviceId, pageable);
    }
}
