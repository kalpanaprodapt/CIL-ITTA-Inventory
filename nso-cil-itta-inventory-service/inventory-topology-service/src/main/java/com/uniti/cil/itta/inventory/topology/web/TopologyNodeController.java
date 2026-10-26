package com.uniti.cil.itta.inventory.topology.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.service.TopologyNodeService;
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
@RequestMapping("/api/v1/topology-nodes")
@Tag(name = "Topology Node", description = "Topology and Links - Topology Node inventory")
public class TopologyNodeController {

    private final TopologyNodeService service;

    public TopologyNodeController(TopologyNodeService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Topology Node records")
    public PageResponse<TopologyNodeResponse> search(TopologyNodeSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Topology Node records")
    public List<TopologyNodeSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Topology Node by id")
    public TopologyNodeResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Topology Node by business code")
    public TopologyNodeResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Topology Node code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Topology Node")
    public ResponseEntity<TopologyNodeResponse> create(@Valid @RequestBody TopologyNodeCreateRequest request) {
        TopologyNodeResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/topology-nodes/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Topology Node")
    public TopologyNodeResponse update(@PathVariable UUID id, @Valid @RequestBody TopologyNodeUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Topology Node")
    public TopologyNodeResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Topology Node")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Topology Node records")
    public BulkResult<TopologyNodeResponse> bulkCreate(@Valid @RequestBody List<@Valid TopologyNodeCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Topology Node records")
    public BulkResult<TopologyNodeResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Topology Node records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Topology Node records as CSV")
    public void export(TopologyNodeSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"topology-nodes.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-view-id/{viewId}")
    @Operation(summary = "List Topology Node records by View Id")
    public PageResponse<TopologyNodeResponse> byViewId(@PathVariable UUID viewId, Pageable pageable) {
        return service.findByViewId(viewId, pageable);
    }
}
