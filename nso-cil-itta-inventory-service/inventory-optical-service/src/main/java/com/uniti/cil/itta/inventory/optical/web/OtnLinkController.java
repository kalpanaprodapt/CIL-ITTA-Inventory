package com.uniti.cil.itta.inventory.optical.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.service.OtnLinkService;
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
@RequestMapping("/api/v1/otn-links")
@Tag(name = "Otn Link", description = "Optical - Otn Link inventory")
public class OtnLinkController {

    private final OtnLinkService service;

    public OtnLinkController(OtnLinkService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Otn Link records")
    public PageResponse<OtnLinkResponse> search(OtnLinkSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Otn Link records")
    public List<OtnLinkSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Otn Link by id")
    public OtnLinkResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Otn Link by business code")
    public OtnLinkResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Otn Link code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Otn Link")
    public ResponseEntity<OtnLinkResponse> create(@Valid @RequestBody OtnLinkCreateRequest request) {
        OtnLinkResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/otn-links/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Otn Link")
    public OtnLinkResponse update(@PathVariable UUID id, @Valid @RequestBody OtnLinkUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Otn Link")
    public OtnLinkResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Otn Link")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Otn Link records")
    public BulkResult<OtnLinkResponse> bulkCreate(@Valid @RequestBody List<@Valid OtnLinkCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Otn Link records")
    public BulkResult<OtnLinkResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Otn Link records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Otn Link records as CSV")
    public void export(OtnLinkSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"otn-links.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-a-node-id/{aNodeId}")
    @Operation(summary = "List Otn Link records by A Node Id")
    public PageResponse<OtnLinkResponse> byANodeId(@PathVariable UUID aNodeId, Pageable pageable) {
        return service.findByANodeId(aNodeId, pageable);
    }

    @GetMapping("/by-z-node-id/{zNodeId}")
    @Operation(summary = "List Otn Link records by Z Node Id")
    public PageResponse<OtnLinkResponse> byZNodeId(@PathVariable UUID zNodeId, Pageable pageable) {
        return service.findByZNodeId(zNodeId, pageable);
    }
}
