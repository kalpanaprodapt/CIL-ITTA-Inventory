package com.uniti.cil.itta.inventory.change.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.service.FieldTaskService;
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
@RequestMapping("/api/v1/field-tasks")
@Tag(name = "Field Task", description = "Change and Work Management - Field Task inventory")
public class FieldTaskController {

    private final FieldTaskService service;

    public FieldTaskController(FieldTaskService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Field Task records")
    public PageResponse<FieldTaskResponse> search(FieldTaskSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Field Task records")
    public List<FieldTaskSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Field Task by id")
    public FieldTaskResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Field Task by business code")
    public FieldTaskResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Field Task code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Field Task")
    public ResponseEntity<FieldTaskResponse> create(@Valid @RequestBody FieldTaskCreateRequest request) {
        FieldTaskResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/field-tasks/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Field Task")
    public FieldTaskResponse update(@PathVariable UUID id, @Valid @RequestBody FieldTaskUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Field Task")
    public FieldTaskResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Field Task")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Field Task records")
    public BulkResult<FieldTaskResponse> bulkCreate(@Valid @RequestBody List<@Valid FieldTaskCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Field Task records")
    public BulkResult<FieldTaskResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Field Task records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Field Task records as CSV")
    public void export(FieldTaskSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"field-tasks.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-work-order-id/{workOrderId}")
    @Operation(summary = "List Field Task records by Work Order Id")
    public PageResponse<FieldTaskResponse> byWorkOrderId(@PathVariable UUID workOrderId, Pageable pageable) {
        return service.findByWorkOrderId(workOrderId, pageable);
    }
}
