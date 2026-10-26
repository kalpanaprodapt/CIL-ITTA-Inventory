package com.uniti.cil.itta.inventory.circuit.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.service.CircuitProviderService;
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
@RequestMapping("/api/v1/circuit-providers")
@Tag(name = "Circuit Provider", description = "Circuits - Circuit Provider inventory")
public class CircuitProviderController {

    private final CircuitProviderService service;

    public CircuitProviderController(CircuitProviderService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Circuit Provider records")
    public PageResponse<CircuitProviderResponse> search(CircuitProviderSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Circuit Provider records")
    public List<CircuitProviderSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Circuit Provider by id")
    public CircuitProviderResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Circuit Provider by business code")
    public CircuitProviderResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Circuit Provider code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Circuit Provider")
    public ResponseEntity<CircuitProviderResponse> create(@Valid @RequestBody CircuitProviderCreateRequest request) {
        CircuitProviderResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/circuit-providers/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Circuit Provider")
    public CircuitProviderResponse update(@PathVariable UUID id, @Valid @RequestBody CircuitProviderUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Circuit Provider")
    public CircuitProviderResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Circuit Provider")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Circuit Provider records")
    public BulkResult<CircuitProviderResponse> bulkCreate(@Valid @RequestBody List<@Valid CircuitProviderCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Circuit Provider records")
    public BulkResult<CircuitProviderResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Circuit Provider records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Circuit Provider records as CSV")
    public void export(CircuitProviderSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"circuit-providers.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }
}
