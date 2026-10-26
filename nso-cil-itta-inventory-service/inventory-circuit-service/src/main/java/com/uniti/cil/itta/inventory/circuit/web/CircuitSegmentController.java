package com.uniti.cil.itta.inventory.circuit.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.service.CircuitSegmentService;
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
@RequestMapping("/api/v1/circuit-segments")
@Tag(name = "Circuit Segment", description = "Circuits - Circuit Segment inventory")
public class CircuitSegmentController {

    private final CircuitSegmentService service;

    public CircuitSegmentController(CircuitSegmentService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Circuit Segment records")
    public PageResponse<CircuitSegmentResponse> search(CircuitSegmentSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Circuit Segment records")
    public List<CircuitSegmentSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Circuit Segment by id")
    public CircuitSegmentResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Circuit Segment by business code")
    public CircuitSegmentResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Circuit Segment code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Circuit Segment")
    public ResponseEntity<CircuitSegmentResponse> create(@Valid @RequestBody CircuitSegmentCreateRequest request) {
        CircuitSegmentResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/circuit-segments/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Circuit Segment")
    public CircuitSegmentResponse update(@PathVariable UUID id, @Valid @RequestBody CircuitSegmentUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Circuit Segment")
    public CircuitSegmentResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Circuit Segment")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Circuit Segment records")
    public BulkResult<CircuitSegmentResponse> bulkCreate(@Valid @RequestBody List<@Valid CircuitSegmentCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Circuit Segment records")
    public BulkResult<CircuitSegmentResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Circuit Segment records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Circuit Segment records as CSV")
    public void export(CircuitSegmentSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"circuit-segments.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-circuit-id/{circuitId}")
    @Operation(summary = "List Circuit Segment records by Circuit Id")
    public PageResponse<CircuitSegmentResponse> byCircuitId(@PathVariable UUID circuitId, Pageable pageable) {
        return service.findByCircuitId(circuitId, pageable);
    }

    @GetMapping("/by-a-termination-id/{aTerminationId}")
    @Operation(summary = "List Circuit Segment records by A Termination Id")
    public PageResponse<CircuitSegmentResponse> byATerminationId(@PathVariable UUID aTerminationId, Pageable pageable) {
        return service.findByATerminationId(aTerminationId, pageable);
    }

    @GetMapping("/by-z-termination-id/{zTerminationId}")
    @Operation(summary = "List Circuit Segment records by Z Termination Id")
    public PageResponse<CircuitSegmentResponse> byZTerminationId(@PathVariable UUID zTerminationId, Pageable pageable) {
        return service.findByZTerminationId(zTerminationId, pageable);
    }
}
