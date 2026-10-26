package com.uniti.cil.itta.inventory.alarm.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.service.AlarmDefinitionService;
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
@RequestMapping("/api/v1/alarm-definitions")
@Tag(name = "Alarm Definition", description = "Alarms and Events - Alarm Definition inventory")
public class AlarmDefinitionController {

    private final AlarmDefinitionService service;

    public AlarmDefinitionController(AlarmDefinitionService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Alarm Definition records")
    public PageResponse<AlarmDefinitionResponse> search(AlarmDefinitionSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Alarm Definition records")
    public List<AlarmDefinitionSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Alarm Definition by id")
    public AlarmDefinitionResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Alarm Definition by business code")
    public AlarmDefinitionResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Alarm Definition code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Alarm Definition")
    public ResponseEntity<AlarmDefinitionResponse> create(@Valid @RequestBody AlarmDefinitionCreateRequest request) {
        AlarmDefinitionResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/alarm-definitions/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Alarm Definition")
    public AlarmDefinitionResponse update(@PathVariable UUID id, @Valid @RequestBody AlarmDefinitionUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Alarm Definition")
    public AlarmDefinitionResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Alarm Definition")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Alarm Definition records")
    public BulkResult<AlarmDefinitionResponse> bulkCreate(@Valid @RequestBody List<@Valid AlarmDefinitionCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Alarm Definition records")
    public BulkResult<AlarmDefinitionResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Alarm Definition records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Alarm Definition records as CSV")
    public void export(AlarmDefinitionSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"alarm-definitions.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }
}
