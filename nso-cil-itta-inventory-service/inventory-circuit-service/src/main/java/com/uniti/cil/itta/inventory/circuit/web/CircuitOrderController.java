package com.uniti.cil.itta.inventory.circuit.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.service.CircuitOrderService;
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
@RequestMapping("/api/v1/circuit-orders")
@Tag(name = "Circuit Order", description = "Circuits - Circuit Order inventory")
public class CircuitOrderController {

    private final CircuitOrderService service;

    public CircuitOrderController(CircuitOrderService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Circuit Order records")
    public PageResponse<CircuitOrderResponse> search(CircuitOrderSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Circuit Order records")
    public List<CircuitOrderSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Circuit Order by id")
    public CircuitOrderResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Circuit Order by business code")
    public CircuitOrderResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Circuit Order code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Circuit Order")
    public ResponseEntity<CircuitOrderResponse> create(@Valid @RequestBody CircuitOrderCreateRequest request) {
        CircuitOrderResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/circuit-orders/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Circuit Order")
    public CircuitOrderResponse update(@PathVariable UUID id, @Valid @RequestBody CircuitOrderUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Circuit Order")
    public CircuitOrderResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Circuit Order")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Circuit Order records")
    public BulkResult<CircuitOrderResponse> bulkCreate(@Valid @RequestBody List<@Valid CircuitOrderCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Circuit Order records")
    public BulkResult<CircuitOrderResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Circuit Order records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Circuit Order records as CSV")
    public void export(CircuitOrderSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"circuit-orders.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-circuit-id/{circuitId}")
    @Operation(summary = "List Circuit Order records by Circuit Id")
    public PageResponse<CircuitOrderResponse> byCircuitId(@PathVariable UUID circuitId, Pageable pageable) {
        return service.findByCircuitId(circuitId, pageable);
    }
}
