package com.uniti.cil.itta.inventory.switching.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.service.RouteTargetService;
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
@RequestMapping("/api/v1/route-targets")
@Tag(name = "Route Target", description = "L2 and L3 Routing - Route Target inventory")
public class RouteTargetController {

    private final RouteTargetService service;

    public RouteTargetController(RouteTargetService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Route Target records")
    public PageResponse<RouteTargetResponse> search(RouteTargetSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Route Target records")
    public List<RouteTargetSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Route Target by id")
    public RouteTargetResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Route Target by business code")
    public RouteTargetResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Route Target code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Route Target")
    public ResponseEntity<RouteTargetResponse> create(@Valid @RequestBody RouteTargetCreateRequest request) {
        RouteTargetResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/route-targets/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Route Target")
    public RouteTargetResponse update(@PathVariable UUID id, @Valid @RequestBody RouteTargetUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Route Target")
    public RouteTargetResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Route Target")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Route Target records")
    public BulkResult<RouteTargetResponse> bulkCreate(@Valid @RequestBody List<@Valid RouteTargetCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Route Target records")
    public BulkResult<RouteTargetResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Route Target records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Route Target records as CSV")
    public void export(RouteTargetSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"route-targets.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-vrf-id/{vrfId}")
    @Operation(summary = "List Route Target records by Vrf Id")
    public PageResponse<RouteTargetResponse> byVrfId(@PathVariable UUID vrfId, Pageable pageable) {
        return service.findByVrfId(vrfId, pageable);
    }
}
