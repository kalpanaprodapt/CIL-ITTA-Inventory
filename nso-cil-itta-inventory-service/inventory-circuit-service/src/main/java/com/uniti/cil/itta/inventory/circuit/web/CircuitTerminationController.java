package com.uniti.cil.itta.inventory.circuit.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.service.CircuitTerminationService;
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
@RequestMapping("/api/v1/circuit-terminations")
@Tag(name = "Circuit Termination", description = "Circuits - Circuit Termination inventory")
public class CircuitTerminationController {

    private final CircuitTerminationService service;

    public CircuitTerminationController(CircuitTerminationService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Circuit Termination records")
    public PageResponse<CircuitTerminationResponse> search(CircuitTerminationSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Circuit Termination records")
    public List<CircuitTerminationSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Circuit Termination by id")
    public CircuitTerminationResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Circuit Termination by business code")
    public CircuitTerminationResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Circuit Termination code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Circuit Termination")
    public ResponseEntity<CircuitTerminationResponse> create(@Valid @RequestBody CircuitTerminationCreateRequest request) {
        CircuitTerminationResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/circuit-terminations/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Circuit Termination")
    public CircuitTerminationResponse update(@PathVariable UUID id, @Valid @RequestBody CircuitTerminationUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Circuit Termination")
    public CircuitTerminationResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Circuit Termination")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Circuit Termination records")
    public BulkResult<CircuitTerminationResponse> bulkCreate(@Valid @RequestBody List<@Valid CircuitTerminationCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Circuit Termination records")
    public BulkResult<CircuitTerminationResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Circuit Termination records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Circuit Termination records as CSV")
    public void export(CircuitTerminationSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"circuit-terminations.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-circuit-id/{circuitId}")
    @Operation(summary = "List Circuit Termination records by Circuit Id")
    public PageResponse<CircuitTerminationResponse> byCircuitId(@PathVariable UUID circuitId, Pageable pageable) {
        return service.findByCircuitId(circuitId, pageable);
    }

    @GetMapping("/by-port-id/{portId}")
    @Operation(summary = "List Circuit Termination records by Port Id")
    public PageResponse<CircuitTerminationResponse> byPortId(@PathVariable UUID portId, Pageable pageable) {
        return service.findByPortId(portId, pageable);
    }
}
