package com.uniti.cil.itta.inventory.topology.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.service.TopologyEdgeService;
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
@RequestMapping("/api/v1/topology-edges")
@Tag(name = "Topology Edge", description = "Topology and Links - Topology Edge inventory")
public class TopologyEdgeController {

    private final TopologyEdgeService service;

    public TopologyEdgeController(TopologyEdgeService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Topology Edge records")
    public PageResponse<TopologyEdgeResponse> search(TopologyEdgeSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Topology Edge records")
    public List<TopologyEdgeSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Topology Edge by id")
    public TopologyEdgeResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Topology Edge by business code")
    public TopologyEdgeResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Topology Edge code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Topology Edge")
    public ResponseEntity<TopologyEdgeResponse> create(@Valid @RequestBody TopologyEdgeCreateRequest request) {
        TopologyEdgeResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/topology-edges/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Topology Edge")
    public TopologyEdgeResponse update(@PathVariable UUID id, @Valid @RequestBody TopologyEdgeUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Topology Edge")
    public TopologyEdgeResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Topology Edge")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Topology Edge records")
    public BulkResult<TopologyEdgeResponse> bulkCreate(@Valid @RequestBody List<@Valid TopologyEdgeCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Topology Edge records")
    public BulkResult<TopologyEdgeResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Topology Edge records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Topology Edge records as CSV")
    public void export(TopologyEdgeSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"topology-edges.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-view-id/{viewId}")
    @Operation(summary = "List Topology Edge records by View Id")
    public PageResponse<TopologyEdgeResponse> byViewId(@PathVariable UUID viewId, Pageable pageable) {
        return service.findByViewId(viewId, pageable);
    }

    @GetMapping("/by-source-node-id/{sourceNodeId}")
    @Operation(summary = "List Topology Edge records by Source Node Id")
    public PageResponse<TopologyEdgeResponse> bySourceNodeId(@PathVariable UUID sourceNodeId, Pageable pageable) {
        return service.findBySourceNodeId(sourceNodeId, pageable);
    }

    @GetMapping("/by-target-node-id/{targetNodeId}")
    @Operation(summary = "List Topology Edge records by Target Node Id")
    public PageResponse<TopologyEdgeResponse> byTargetNodeId(@PathVariable UUID targetNodeId, Pageable pageable) {
        return service.findByTargetNodeId(targetNodeId, pageable);
    }
}
