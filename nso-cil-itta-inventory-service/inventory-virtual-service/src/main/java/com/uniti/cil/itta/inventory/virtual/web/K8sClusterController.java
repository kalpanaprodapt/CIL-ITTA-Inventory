package com.uniti.cil.itta.inventory.virtual.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.service.K8sClusterService;
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
@RequestMapping("/api/v1/k8s-clusters")
@Tag(name = "K8s Cluster", description = "Virtual and Cloud - K8s Cluster inventory")
public class K8sClusterController {

    private final K8sClusterService service;

    public K8sClusterController(K8sClusterService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search K8s Cluster records")
    public PageResponse<K8sClusterResponse> search(K8sClusterSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of K8s Cluster records")
    public List<K8sClusterSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a K8s Cluster by id")
    public K8sClusterResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a K8s Cluster by business code")
    public K8sClusterResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a K8s Cluster code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a K8s Cluster")
    public ResponseEntity<K8sClusterResponse> create(@Valid @RequestBody K8sClusterCreateRequest request) {
        K8sClusterResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/k8s-clusters/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a K8s Cluster")
    public K8sClusterResponse update(@PathVariable UUID id, @Valid @RequestBody K8sClusterUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a K8s Cluster")
    public K8sClusterResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a K8s Cluster")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many K8s Cluster records")
    public BulkResult<K8sClusterResponse> bulkCreate(@Valid @RequestBody List<@Valid K8sClusterCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many K8s Cluster records")
    public BulkResult<K8sClusterResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of K8s Cluster records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export K8s Cluster records as CSV")
    public void export(K8sClusterSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"k8s-clusters.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-cloud-account-id/{cloudAccountId}")
    @Operation(summary = "List K8s Cluster records by Cloud Account Id")
    public PageResponse<K8sClusterResponse> byCloudAccountId(@PathVariable UUID cloudAccountId, Pageable pageable) {
        return service.findByCloudAccountId(cloudAccountId, pageable);
    }
}
