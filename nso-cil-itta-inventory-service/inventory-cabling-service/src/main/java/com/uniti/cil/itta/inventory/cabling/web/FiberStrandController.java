package com.uniti.cil.itta.inventory.cabling.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.service.FiberStrandService;
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
@RequestMapping("/api/v1/fiber-strands")
@Tag(name = "Fiber Strand", description = "Cabling - Fiber Strand inventory")
public class FiberStrandController {

    private final FiberStrandService service;

    public FiberStrandController(FiberStrandService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Fiber Strand records")
    public PageResponse<FiberStrandResponse> search(FiberStrandSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Fiber Strand records")
    public List<FiberStrandSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Fiber Strand by id")
    public FiberStrandResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Fiber Strand by business code")
    public FiberStrandResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Fiber Strand code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Fiber Strand")
    public ResponseEntity<FiberStrandResponse> create(@Valid @RequestBody FiberStrandCreateRequest request) {
        FiberStrandResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/fiber-strands/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Fiber Strand")
    public FiberStrandResponse update(@PathVariable UUID id, @Valid @RequestBody FiberStrandUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Fiber Strand")
    public FiberStrandResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Fiber Strand")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Fiber Strand records")
    public BulkResult<FiberStrandResponse> bulkCreate(@Valid @RequestBody List<@Valid FiberStrandCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Fiber Strand records")
    public BulkResult<FiberStrandResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Fiber Strand records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Fiber Strand records as CSV")
    public void export(FiberStrandSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"fiber-strands.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-cable-id/{cableId}")
    @Operation(summary = "List Fiber Strand records by Cable Id")
    public PageResponse<FiberStrandResponse> byCableId(@PathVariable UUID cableId, Pageable pageable) {
        return service.findByCableId(cableId, pageable);
    }
}
