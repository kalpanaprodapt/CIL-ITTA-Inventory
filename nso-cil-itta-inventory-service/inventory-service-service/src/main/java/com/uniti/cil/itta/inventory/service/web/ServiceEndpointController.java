package com.uniti.cil.itta.inventory.service.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.service.ServiceEndpointService;
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
@RequestMapping("/api/v1/service-endpoints")
@Tag(name = "Service Endpoint", description = "Services - Service Endpoint inventory")
public class ServiceEndpointController {

    private final ServiceEndpointService service;

    public ServiceEndpointController(ServiceEndpointService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Service Endpoint records")
    public PageResponse<ServiceEndpointResponse> search(ServiceEndpointSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Service Endpoint records")
    public List<ServiceEndpointSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Service Endpoint by id")
    public ServiceEndpointResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Service Endpoint by business code")
    public ServiceEndpointResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Service Endpoint code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Service Endpoint")
    public ResponseEntity<ServiceEndpointResponse> create(@Valid @RequestBody ServiceEndpointCreateRequest request) {
        ServiceEndpointResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/service-endpoints/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Service Endpoint")
    public ServiceEndpointResponse update(@PathVariable UUID id, @Valid @RequestBody ServiceEndpointUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Service Endpoint")
    public ServiceEndpointResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Service Endpoint")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Service Endpoint records")
    public BulkResult<ServiceEndpointResponse> bulkCreate(@Valid @RequestBody List<@Valid ServiceEndpointCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Service Endpoint records")
    public BulkResult<ServiceEndpointResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Service Endpoint records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Service Endpoint records as CSV")
    public void export(ServiceEndpointSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"service-endpoints.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-service-id/{serviceId}")
    @Operation(summary = "List Service Endpoint records by Service Id")
    public PageResponse<ServiceEndpointResponse> byServiceId(@PathVariable UUID serviceId, Pageable pageable) {
        return service.findByServiceId(serviceId, pageable);
    }

    @GetMapping("/by-site-id/{siteId}")
    @Operation(summary = "List Service Endpoint records by Site Id")
    public PageResponse<ServiceEndpointResponse> bySiteId(@PathVariable UUID siteId, Pageable pageable) {
        return service.findBySiteId(siteId, pageable);
    }

    @GetMapping("/by-port-id/{portId}")
    @Operation(summary = "List Service Endpoint records by Port Id")
    public PageResponse<ServiceEndpointResponse> byPortId(@PathVariable UUID portId, Pageable pageable) {
        return service.findByPortId(portId, pageable);
    }
}
