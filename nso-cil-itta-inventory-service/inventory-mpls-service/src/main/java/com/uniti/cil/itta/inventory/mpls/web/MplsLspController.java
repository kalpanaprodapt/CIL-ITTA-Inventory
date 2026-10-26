package com.uniti.cil.itta.inventory.mpls.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.service.MplsLspService;
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
@RequestMapping("/api/v1/mpls-lsps")
@Tag(name = "Mpls Lsp", description = "MPLS and VPN - Mpls Lsp inventory")
public class MplsLspController {

    private final MplsLspService service;

    public MplsLspController(MplsLspService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Mpls Lsp records")
    public PageResponse<MplsLspResponse> search(MplsLspSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Mpls Lsp records")
    public List<MplsLspSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Mpls Lsp by id")
    public MplsLspResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Mpls Lsp by business code")
    public MplsLspResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Mpls Lsp code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Mpls Lsp")
    public ResponseEntity<MplsLspResponse> create(@Valid @RequestBody MplsLspCreateRequest request) {
        MplsLspResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/mpls-lsps/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Mpls Lsp")
    public MplsLspResponse update(@PathVariable UUID id, @Valid @RequestBody MplsLspUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Mpls Lsp")
    public MplsLspResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Mpls Lsp")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Mpls Lsp records")
    public BulkResult<MplsLspResponse> bulkCreate(@Valid @RequestBody List<@Valid MplsLspCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Mpls Lsp records")
    public BulkResult<MplsLspResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Mpls Lsp records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Mpls Lsp records as CSV")
    public void export(MplsLspSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"mpls-lsps.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-head-end-id/{headEndId}")
    @Operation(summary = "List Mpls Lsp records by Head End Id")
    public PageResponse<MplsLspResponse> byHeadEndId(@PathVariable UUID headEndId, Pageable pageable) {
        return service.findByHeadEndId(headEndId, pageable);
    }

    @GetMapping("/by-tail-end-id/{tailEndId}")
    @Operation(summary = "List Mpls Lsp records by Tail End Id")
    public PageResponse<MplsLspResponse> byTailEndId(@PathVariable UUID tailEndId, Pageable pageable) {
        return service.findByTailEndId(tailEndId, pageable);
    }
}
