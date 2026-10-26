package com.uniti.cil.itta.inventory.capacity.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.service.UtilizationSampleService;
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
@RequestMapping("/api/v1/utilization-samples")
@Tag(name = "Utilization Sample", description = "Capacity Planning - Utilization Sample inventory")
public class UtilizationSampleController {

    private final UtilizationSampleService service;

    public UtilizationSampleController(UtilizationSampleService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Utilization Sample records")
    public PageResponse<UtilizationSampleResponse> search(UtilizationSampleSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Utilization Sample records")
    public List<UtilizationSampleSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Utilization Sample by id")
    public UtilizationSampleResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Utilization Sample by business code")
    public UtilizationSampleResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Utilization Sample code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Utilization Sample")
    public ResponseEntity<UtilizationSampleResponse> create(@Valid @RequestBody UtilizationSampleCreateRequest request) {
        UtilizationSampleResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/utilization-samples/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Utilization Sample")
    public UtilizationSampleResponse update(@PathVariable UUID id, @Valid @RequestBody UtilizationSampleUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Utilization Sample")
    public UtilizationSampleResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Utilization Sample")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Utilization Sample records")
    public BulkResult<UtilizationSampleResponse> bulkCreate(@Valid @RequestBody List<@Valid UtilizationSampleCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Utilization Sample records")
    public BulkResult<UtilizationSampleResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Utilization Sample records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Utilization Sample records as CSV")
    public void export(UtilizationSampleSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"utilization-samples.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }
}
