package com.uniti.cil.itta.inventory.radio.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.service.AntennaService;
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
@RequestMapping("/api/v1/antennas")
@Tag(name = "Antenna", description = "Radio and Mobile - Antenna inventory")
public class AntennaController {

    private final AntennaService service;

    public AntennaController(AntennaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Antenna records")
    public PageResponse<AntennaResponse> search(AntennaSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Antenna records")
    public List<AntennaSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Antenna by id")
    public AntennaResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Antenna by business code")
    public AntennaResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Antenna code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Antenna")
    public ResponseEntity<AntennaResponse> create(@Valid @RequestBody AntennaCreateRequest request) {
        AntennaResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/antennas/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Antenna")
    public AntennaResponse update(@PathVariable UUID id, @Valid @RequestBody AntennaUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Antenna")
    public AntennaResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Antenna")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Antenna records")
    public BulkResult<AntennaResponse> bulkCreate(@Valid @RequestBody List<@Valid AntennaCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Antenna records")
    public BulkResult<AntennaResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Antenna records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Antenna records as CSV")
    public void export(AntennaSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"antennas.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-sector-id/{sectorId}")
    @Operation(summary = "List Antenna records by Sector Id")
    public PageResponse<AntennaResponse> bySectorId(@PathVariable UUID sectorId, Pageable pageable) {
        return service.findBySectorId(sectorId, pageable);
    }
}
