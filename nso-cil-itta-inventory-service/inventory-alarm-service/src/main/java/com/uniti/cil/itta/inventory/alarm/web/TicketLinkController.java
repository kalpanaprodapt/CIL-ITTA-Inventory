package com.uniti.cil.itta.inventory.alarm.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.service.TicketLinkService;
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
@RequestMapping("/api/v1/ticket-links")
@Tag(name = "Ticket Link", description = "Alarms and Events - Ticket Link inventory")
public class TicketLinkController {

    private final TicketLinkService service;

    public TicketLinkController(TicketLinkService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Ticket Link records")
    public PageResponse<TicketLinkResponse> search(TicketLinkSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Ticket Link records")
    public List<TicketLinkSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Ticket Link by id")
    public TicketLinkResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Ticket Link by business code")
    public TicketLinkResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Ticket Link code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Ticket Link")
    public ResponseEntity<TicketLinkResponse> create(@Valid @RequestBody TicketLinkCreateRequest request) {
        TicketLinkResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/ticket-links/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Ticket Link")
    public TicketLinkResponse update(@PathVariable UUID id, @Valid @RequestBody TicketLinkUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Ticket Link")
    public TicketLinkResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Ticket Link")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Ticket Link records")
    public BulkResult<TicketLinkResponse> bulkCreate(@Valid @RequestBody List<@Valid TicketLinkCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Ticket Link records")
    public BulkResult<TicketLinkResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Ticket Link records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Ticket Link records as CSV")
    public void export(TicketLinkSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"ticket-links.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-alarm-id/{alarmId}")
    @Operation(summary = "List Ticket Link records by Alarm Id")
    public PageResponse<TicketLinkResponse> byAlarmId(@PathVariable UUID alarmId, Pageable pageable) {
        return service.findByAlarmId(alarmId, pageable);
    }
}
