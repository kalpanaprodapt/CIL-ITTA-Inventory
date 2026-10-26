package com.uniti.cil.itta.inventory.port.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.service.SubInterfaceService;
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
@RequestMapping("/api/v1/sub-interfaces")
@Tag(name = "Sub Interface", description = "Port and Interface - Sub Interface inventory")
public class SubInterfaceController {

    private final SubInterfaceService service;

    public SubInterfaceController(SubInterfaceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Sub Interface records")
    public PageResponse<SubInterfaceResponse> search(SubInterfaceSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Sub Interface records")
    public List<SubInterfaceSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Sub Interface by id")
    public SubInterfaceResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Sub Interface by business code")
    public SubInterfaceResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Sub Interface code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Sub Interface")
    public ResponseEntity<SubInterfaceResponse> create(@Valid @RequestBody SubInterfaceCreateRequest request) {
        SubInterfaceResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/sub-interfaces/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Sub Interface")
    public SubInterfaceResponse update(@PathVariable UUID id, @Valid @RequestBody SubInterfaceUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Sub Interface")
    public SubInterfaceResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Sub Interface")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Sub Interface records")
    public BulkResult<SubInterfaceResponse> bulkCreate(@Valid @RequestBody List<@Valid SubInterfaceCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Sub Interface records")
    public BulkResult<SubInterfaceResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Sub Interface records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Sub Interface records as CSV")
    public void export(SubInterfaceSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"sub-interfaces.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-interface-id/{interfaceId}")
    @Operation(summary = "List Sub Interface records by Interface Id")
    public PageResponse<SubInterfaceResponse> byInterfaceId(@PathVariable UUID interfaceId, Pageable pageable) {
        return service.findByInterfaceId(interfaceId, pageable);
    }
}
