package com.uniti.cil.itta.inventory.optical.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.service.AmplifierService;
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
@RequestMapping("/api/v1/amplifiers")
@Tag(name = "Amplifier", description = "Optical - Amplifier inventory")
public class AmplifierController {

    private final AmplifierService service;

    public AmplifierController(AmplifierService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Amplifier records")
    public PageResponse<AmplifierResponse> search(AmplifierSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Amplifier records")
    public List<AmplifierSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Amplifier by id")
    public AmplifierResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Amplifier by business code")
    public AmplifierResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Amplifier code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Amplifier")
    public ResponseEntity<AmplifierResponse> create(@Valid @RequestBody AmplifierCreateRequest request) {
        AmplifierResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/amplifiers/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Amplifier")
    public AmplifierResponse update(@PathVariable UUID id, @Valid @RequestBody AmplifierUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Amplifier")
    public AmplifierResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Amplifier")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Amplifier records")
    public BulkResult<AmplifierResponse> bulkCreate(@Valid @RequestBody List<@Valid AmplifierCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Amplifier records")
    public BulkResult<AmplifierResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Amplifier records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Amplifier records as CSV")
    public void export(AmplifierSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"amplifiers.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-system-id/{systemId}")
    @Operation(summary = "List Amplifier records by System Id")
    public PageResponse<AmplifierResponse> bySystemId(@PathVariable UUID systemId, Pageable pageable) {
        return service.findBySystemId(systemId, pageable);
    }
}
