package com.uniti.cil.itta.inventory.alarm.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.service.AlarmInstanceService;
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
@RequestMapping("/api/v1/alarm-instances")
@Tag(name = "Alarm Instance", description = "Alarms and Events - Alarm Instance inventory")
public class AlarmInstanceController {

    private final AlarmInstanceService service;

    public AlarmInstanceController(AlarmInstanceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Alarm Instance records")
    public PageResponse<AlarmInstanceResponse> search(AlarmInstanceSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Alarm Instance records")
    public List<AlarmInstanceSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Alarm Instance by id")
    public AlarmInstanceResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Alarm Instance by business code")
    public AlarmInstanceResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Alarm Instance code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Alarm Instance")
    public ResponseEntity<AlarmInstanceResponse> create(@Valid @RequestBody AlarmInstanceCreateRequest request) {
        AlarmInstanceResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/alarm-instances/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Alarm Instance")
    public AlarmInstanceResponse update(@PathVariable UUID id, @Valid @RequestBody AlarmInstanceUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Alarm Instance")
    public AlarmInstanceResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Alarm Instance")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Alarm Instance records")
    public BulkResult<AlarmInstanceResponse> bulkCreate(@Valid @RequestBody List<@Valid AlarmInstanceCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Alarm Instance records")
    public BulkResult<AlarmInstanceResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Alarm Instance records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Alarm Instance records as CSV")
    public void export(AlarmInstanceSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"alarm-instances.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-definition-id/{definitionId}")
    @Operation(summary = "List Alarm Instance records by Definition Id")
    public PageResponse<AlarmInstanceResponse> byDefinitionId(@PathVariable UUID definitionId, Pageable pageable) {
        return service.findByDefinitionId(definitionId, pageable);
    }
}
