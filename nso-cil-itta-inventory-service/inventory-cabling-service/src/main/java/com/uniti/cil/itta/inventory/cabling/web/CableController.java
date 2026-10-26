package com.uniti.cil.itta.inventory.cabling.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.service.CableService;
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
@RequestMapping("/api/v1/cables")
@Tag(name = "Cable", description = "Cabling - Cable inventory")
public class CableController {

    private final CableService service;

    public CableController(CableService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Cable records")
    public PageResponse<CableResponse> search(CableSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Cable records")
    public List<CableSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Cable by id")
    public CableResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Cable by business code")
    public CableResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Cable code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Cable")
    public ResponseEntity<CableResponse> create(@Valid @RequestBody CableCreateRequest request) {
        CableResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/cables/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Cable")
    public CableResponse update(@PathVariable UUID id, @Valid @RequestBody CableUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Cable")
    public CableResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Cable")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Cable records")
    public BulkResult<CableResponse> bulkCreate(@Valid @RequestBody List<@Valid CableCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Cable records")
    public BulkResult<CableResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Cable records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Cable records as CSV")
    public void export(CableSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"cables.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-a-end-port-id/{aEndPortId}")
    @Operation(summary = "List Cable records by A End Port Id")
    public PageResponse<CableResponse> byAEndPortId(@PathVariable UUID aEndPortId, Pageable pageable) {
        return service.findByAEndPortId(aEndPortId, pageable);
    }

    @GetMapping("/by-z-end-port-id/{zEndPortId}")
    @Operation(summary = "List Cable records by Z End Port Id")
    public PageResponse<CableResponse> byZEndPortId(@PathVariable UUID zEndPortId, Pageable pageable) {
        return service.findByZEndPortId(zEndPortId, pageable);
    }
}
