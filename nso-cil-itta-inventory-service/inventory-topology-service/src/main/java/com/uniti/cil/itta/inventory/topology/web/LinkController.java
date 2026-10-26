package com.uniti.cil.itta.inventory.topology.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.service.LinkService;
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
@RequestMapping("/api/v1/links")
@Tag(name = "Link", description = "Topology and Links - Link inventory")
public class LinkController {

    private final LinkService service;

    public LinkController(LinkService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Link records")
    public PageResponse<LinkResponse> search(LinkSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Link records")
    public List<LinkSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Link by id")
    public LinkResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Link by business code")
    public LinkResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Link code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Link")
    public ResponseEntity<LinkResponse> create(@Valid @RequestBody LinkCreateRequest request) {
        LinkResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/links/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Link")
    public LinkResponse update(@PathVariable UUID id, @Valid @RequestBody LinkUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Link")
    public LinkResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Link")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Link records")
    public BulkResult<LinkResponse> bulkCreate(@Valid @RequestBody List<@Valid LinkCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Link records")
    public BulkResult<LinkResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Link records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Link records as CSV")
    public void export(LinkSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"links.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-a-interface-id/{aInterfaceId}")
    @Operation(summary = "List Link records by A Interface Id")
    public PageResponse<LinkResponse> byAInterfaceId(@PathVariable UUID aInterfaceId, Pageable pageable) {
        return service.findByAInterfaceId(aInterfaceId, pageable);
    }

    @GetMapping("/by-z-interface-id/{zInterfaceId}")
    @Operation(summary = "List Link records by Z Interface Id")
    public PageResponse<LinkResponse> byZInterfaceId(@PathVariable UUID zInterfaceId, Pageable pageable) {
        return service.findByZInterfaceId(zInterfaceId, pageable);
    }
}
