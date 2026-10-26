package com.uniti.cil.itta.inventory.port.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.service.PortService;
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
@RequestMapping("/api/v1/ports")
@Tag(name = "Port", description = "Port and Interface - Port inventory")
public class PortController {

    private final PortService service;

    public PortController(PortService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Port records")
    public PageResponse<PortResponse> search(PortSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Port records")
    public List<PortSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Port by id")
    public PortResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Port by business code")
    public PortResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Port code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Port")
    public ResponseEntity<PortResponse> create(@Valid @RequestBody PortCreateRequest request) {
        PortResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/ports/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Port")
    public PortResponse update(@PathVariable UUID id, @Valid @RequestBody PortUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Port")
    public PortResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Port")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Port records")
    public BulkResult<PortResponse> bulkCreate(@Valid @RequestBody List<@Valid PortCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Port records")
    public BulkResult<PortResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Port records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Port records as CSV")
    public void export(PortSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"ports.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-device-id/{deviceId}")
    @Operation(summary = "List Port records by Device Id")
    public PageResponse<PortResponse> byDeviceId(@PathVariable UUID deviceId, Pageable pageable) {
        return service.findByDeviceId(deviceId, pageable);
    }

    @GetMapping("/by-card-id/{cardId}")
    @Operation(summary = "List Port records by Card Id")
    public PageResponse<PortResponse> byCardId(@PathVariable UUID cardId, Pageable pageable) {
        return service.findByCardId(cardId, pageable);
    }
}
