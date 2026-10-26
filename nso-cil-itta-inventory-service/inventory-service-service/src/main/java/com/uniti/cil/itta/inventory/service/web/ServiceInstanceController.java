package com.uniti.cil.itta.inventory.service.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.service.ServiceInstanceService;
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
@RequestMapping("/api/v1/service-instances")
@Tag(name = "Service Instance", description = "Services - Service Instance inventory")
public class ServiceInstanceController {

    private final ServiceInstanceService service;

    public ServiceInstanceController(ServiceInstanceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Service Instance records")
    public PageResponse<ServiceInstanceResponse> search(ServiceInstanceSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Service Instance records")
    public List<ServiceInstanceSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Service Instance by id")
    public ServiceInstanceResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Service Instance by business code")
    public ServiceInstanceResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Service Instance code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Service Instance")
    public ResponseEntity<ServiceInstanceResponse> create(@Valid @RequestBody ServiceInstanceCreateRequest request) {
        ServiceInstanceResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/service-instances/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Service Instance")
    public ServiceInstanceResponse update(@PathVariable UUID id, @Valid @RequestBody ServiceInstanceUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Service Instance")
    public ServiceInstanceResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Service Instance")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Service Instance records")
    public BulkResult<ServiceInstanceResponse> bulkCreate(@Valid @RequestBody List<@Valid ServiceInstanceCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Service Instance records")
    public BulkResult<ServiceInstanceResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Service Instance records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Service Instance records as CSV")
    public void export(ServiceInstanceSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"service-instances.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-catalog-item-id/{catalogItemId}")
    @Operation(summary = "List Service Instance records by Catalog Item Id")
    public PageResponse<ServiceInstanceResponse> byCatalogItemId(@PathVariable UUID catalogItemId, Pageable pageable) {
        return service.findByCatalogItemId(catalogItemId, pageable);
    }

    @GetMapping("/by-customer-id/{customerId}")
    @Operation(summary = "List Service Instance records by Customer Id")
    public PageResponse<ServiceInstanceResponse> byCustomerId(@PathVariable UUID customerId, Pageable pageable) {
        return service.findByCustomerId(customerId, pageable);
    }
}
