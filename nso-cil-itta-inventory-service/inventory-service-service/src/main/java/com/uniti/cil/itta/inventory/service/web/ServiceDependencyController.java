package com.uniti.cil.itta.inventory.service.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.service.ServiceDependencyService;
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
@RequestMapping("/api/v1/service-dependencies")
@Tag(name = "Service Dependency", description = "Services - Service Dependency inventory")
public class ServiceDependencyController {

    private final ServiceDependencyService service;

    public ServiceDependencyController(ServiceDependencyService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Service Dependency records")
    public PageResponse<ServiceDependencyResponse> search(ServiceDependencySearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Service Dependency records")
    public List<ServiceDependencySummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Service Dependency by id")
    public ServiceDependencyResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Service Dependency by business code")
    public ServiceDependencyResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Service Dependency code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Service Dependency")
    public ResponseEntity<ServiceDependencyResponse> create(@Valid @RequestBody ServiceDependencyCreateRequest request) {
        ServiceDependencyResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/service-dependencies/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Service Dependency")
    public ServiceDependencyResponse update(@PathVariable UUID id, @Valid @RequestBody ServiceDependencyUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Service Dependency")
    public ServiceDependencyResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Service Dependency")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Service Dependency records")
    public BulkResult<ServiceDependencyResponse> bulkCreate(@Valid @RequestBody List<@Valid ServiceDependencyCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Service Dependency records")
    public BulkResult<ServiceDependencyResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Service Dependency records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Service Dependency records as CSV")
    public void export(ServiceDependencySearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"service-dependencies.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-service-id/{serviceId}")
    @Operation(summary = "List Service Dependency records by Service Id")
    public PageResponse<ServiceDependencyResponse> byServiceId(@PathVariable UUID serviceId, Pageable pageable) {
        return service.findByServiceId(serviceId, pageable);
    }

    @GetMapping("/by-depends-on-id/{dependsOnId}")
    @Operation(summary = "List Service Dependency records by Depends On Id")
    public PageResponse<ServiceDependencyResponse> byDependsOnId(@PathVariable UUID dependsOnId, Pageable pageable) {
        return service.findByDependsOnId(dependsOnId, pageable);
    }
}
