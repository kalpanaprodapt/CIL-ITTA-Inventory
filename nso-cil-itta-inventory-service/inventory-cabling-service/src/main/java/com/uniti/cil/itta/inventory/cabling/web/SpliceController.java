package com.uniti.cil.itta.inventory.cabling.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.service.SpliceService;
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
@RequestMapping("/api/v1/splices")
@Tag(name = "Splice", description = "Cabling - Splice inventory")
public class SpliceController {

    private final SpliceService service;

    public SpliceController(SpliceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Splice records")
    public PageResponse<SpliceResponse> search(SpliceSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Splice records")
    public List<SpliceSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Splice by id")
    public SpliceResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Splice by business code")
    public SpliceResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Splice code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Splice")
    public ResponseEntity<SpliceResponse> create(@Valid @RequestBody SpliceCreateRequest request) {
        SpliceResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/splices/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Splice")
    public SpliceResponse update(@PathVariable UUID id, @Valid @RequestBody SpliceUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Splice")
    public SpliceResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Splice")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Splice records")
    public BulkResult<SpliceResponse> bulkCreate(@Valid @RequestBody List<@Valid SpliceCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Splice records")
    public BulkResult<SpliceResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Splice records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Splice records as CSV")
    public void export(SpliceSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"splices.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-a-strand-id/{aStrandId}")
    @Operation(summary = "List Splice records by A Strand Id")
    public PageResponse<SpliceResponse> byAStrandId(@PathVariable UUID aStrandId, Pageable pageable) {
        return service.findByAStrandId(aStrandId, pageable);
    }

    @GetMapping("/by-z-strand-id/{zStrandId}")
    @Operation(summary = "List Splice records by Z Strand Id")
    public PageResponse<SpliceResponse> byZStrandId(@PathVariable UUID zStrandId, Pageable pageable) {
        return service.findByZStrandId(zStrandId, pageable);
    }
}
