package com.uniti.cil.itta.inventory.circuit.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.service.CircuitService;
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
@RequestMapping("/api/v1/circuits")
@Tag(name = "Circuit", description = "Circuits - Circuit inventory")
public class CircuitController {

    private final CircuitService service;

    public CircuitController(CircuitService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Circuit records")
    public PageResponse<CircuitResponse> search(CircuitSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Circuit records")
    public List<CircuitSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Circuit by id")
    public CircuitResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Circuit by business code")
    public CircuitResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Circuit code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Circuit")
    public ResponseEntity<CircuitResponse> create(@Valid @RequestBody CircuitCreateRequest request) {
        CircuitResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/circuits/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Circuit")
    public CircuitResponse update(@PathVariable UUID id, @Valid @RequestBody CircuitUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Circuit")
    public CircuitResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Circuit")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Circuit records")
    public BulkResult<CircuitResponse> bulkCreate(@Valid @RequestBody List<@Valid CircuitCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Circuit records")
    public BulkResult<CircuitResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Circuit records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Circuit records as CSV")
    public void export(CircuitSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"circuits.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-provider-id/{providerId}")
    @Operation(summary = "List Circuit records by Provider Id")
    public PageResponse<CircuitResponse> byProviderId(@PathVariable UUID providerId, Pageable pageable) {
        return service.findByProviderId(providerId, pageable);
    }

    @GetMapping("/by-a-site-id/{aSiteId}")
    @Operation(summary = "List Circuit records by A Site Id")
    public PageResponse<CircuitResponse> byASiteId(@PathVariable UUID aSiteId, Pageable pageable) {
        return service.findByASiteId(aSiteId, pageable);
    }

    @GetMapping("/by-z-site-id/{zSiteId}")
    @Operation(summary = "List Circuit records by Z Site Id")
    public PageResponse<CircuitResponse> byZSiteId(@PathVariable UUID zSiteId, Pageable pageable) {
        return service.findByZSiteId(zSiteId, pageable);
    }
}
