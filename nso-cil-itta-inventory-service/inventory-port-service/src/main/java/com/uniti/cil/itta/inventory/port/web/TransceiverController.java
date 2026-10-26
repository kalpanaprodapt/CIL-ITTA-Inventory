package com.uniti.cil.itta.inventory.port.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.service.TransceiverService;
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
@RequestMapping("/api/v1/transceivers")
@Tag(name = "Transceiver", description = "Port and Interface - Transceiver inventory")
public class TransceiverController {

    private final TransceiverService service;

    public TransceiverController(TransceiverService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Transceiver records")
    public PageResponse<TransceiverResponse> search(TransceiverSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Transceiver records")
    public List<TransceiverSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Transceiver by id")
    public TransceiverResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Transceiver by business code")
    public TransceiverResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Transceiver code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Transceiver")
    public ResponseEntity<TransceiverResponse> create(@Valid @RequestBody TransceiverCreateRequest request) {
        TransceiverResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/transceivers/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Transceiver")
    public TransceiverResponse update(@PathVariable UUID id, @Valid @RequestBody TransceiverUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Transceiver")
    public TransceiverResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Transceiver")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Transceiver records")
    public BulkResult<TransceiverResponse> bulkCreate(@Valid @RequestBody List<@Valid TransceiverCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Transceiver records")
    public BulkResult<TransceiverResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Transceiver records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Transceiver records as CSV")
    public void export(TransceiverSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"transceivers.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-port-id/{portId}")
    @Operation(summary = "List Transceiver records by Port Id")
    public PageResponse<TransceiverResponse> byPortId(@PathVariable UUID portId, Pageable pageable) {
        return service.findByPortId(portId, pageable);
    }
}
