package com.uniti.cil.itta.inventory.integration.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.service.IntegrationEndpointService;
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
@RequestMapping("/api/v1/integration-endpoints")
@Tag(name = "Integration Endpoint", description = "Integration Layer - Integration Endpoint inventory")
public class IntegrationEndpointController {

    private final IntegrationEndpointService service;

    public IntegrationEndpointController(IntegrationEndpointService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Integration Endpoint records")
    public PageResponse<IntegrationEndpointResponse> search(IntegrationEndpointSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Integration Endpoint records")
    public List<IntegrationEndpointSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Integration Endpoint by id")
    public IntegrationEndpointResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Integration Endpoint by business code")
    public IntegrationEndpointResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Integration Endpoint code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Integration Endpoint")
    public ResponseEntity<IntegrationEndpointResponse> create(@Valid @RequestBody IntegrationEndpointCreateRequest request) {
        IntegrationEndpointResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/integration-endpoints/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Integration Endpoint")
    public IntegrationEndpointResponse update(@PathVariable UUID id, @Valid @RequestBody IntegrationEndpointUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Integration Endpoint")
    public IntegrationEndpointResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Integration Endpoint")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Integration Endpoint records")
    public BulkResult<IntegrationEndpointResponse> bulkCreate(@Valid @RequestBody List<@Valid IntegrationEndpointCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Integration Endpoint records")
    public BulkResult<IntegrationEndpointResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Integration Endpoint records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Integration Endpoint records as CSV")
    public void export(IntegrationEndpointSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"integration-endpoints.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }
}
