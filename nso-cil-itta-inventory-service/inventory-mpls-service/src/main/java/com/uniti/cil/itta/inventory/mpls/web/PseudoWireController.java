package com.uniti.cil.itta.inventory.mpls.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.service.PseudoWireService;
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
@RequestMapping("/api/v1/pseudo-wires")
@Tag(name = "Pseudo Wire", description = "MPLS and VPN - Pseudo Wire inventory")
public class PseudoWireController {

    private final PseudoWireService service;

    public PseudoWireController(PseudoWireService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Pseudo Wire records")
    public PageResponse<PseudoWireResponse> search(PseudoWireSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Pseudo Wire records")
    public List<PseudoWireSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Pseudo Wire by id")
    public PseudoWireResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Pseudo Wire by business code")
    public PseudoWireResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Pseudo Wire code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Pseudo Wire")
    public ResponseEntity<PseudoWireResponse> create(@Valid @RequestBody PseudoWireCreateRequest request) {
        PseudoWireResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/pseudo-wires/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Pseudo Wire")
    public PseudoWireResponse update(@PathVariable UUID id, @Valid @RequestBody PseudoWireUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Pseudo Wire")
    public PseudoWireResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Pseudo Wire")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Pseudo Wire records")
    public BulkResult<PseudoWireResponse> bulkCreate(@Valid @RequestBody List<@Valid PseudoWireCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Pseudo Wire records")
    public BulkResult<PseudoWireResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Pseudo Wire records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Pseudo Wire records as CSV")
    public void export(PseudoWireSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"pseudo-wires.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-a-end-id/{aEndId}")
    @Operation(summary = "List Pseudo Wire records by A End Id")
    public PageResponse<PseudoWireResponse> byAEndId(@PathVariable UUID aEndId, Pageable pageable) {
        return service.findByAEndId(aEndId, pageable);
    }

    @GetMapping("/by-z-end-id/{zEndId}")
    @Operation(summary = "List Pseudo Wire records by Z End Id")
    public PageResponse<PseudoWireResponse> byZEndId(@PathVariable UUID zEndId, Pageable pageable) {
        return service.findByZEndId(zEndId, pageable);
    }
}
