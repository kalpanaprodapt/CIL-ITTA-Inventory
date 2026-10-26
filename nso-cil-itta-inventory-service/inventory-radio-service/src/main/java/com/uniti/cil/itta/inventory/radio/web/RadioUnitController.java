package com.uniti.cil.itta.inventory.radio.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.service.RadioUnitService;
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
@RequestMapping("/api/v1/radio-units")
@Tag(name = "Radio Unit", description = "Radio and Mobile - Radio Unit inventory")
public class RadioUnitController {

    private final RadioUnitService service;

    public RadioUnitController(RadioUnitService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Radio Unit records")
    public PageResponse<RadioUnitResponse> search(RadioUnitSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Radio Unit records")
    public List<RadioUnitSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Radio Unit by id")
    public RadioUnitResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Radio Unit by business code")
    public RadioUnitResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Radio Unit code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Radio Unit")
    public ResponseEntity<RadioUnitResponse> create(@Valid @RequestBody RadioUnitCreateRequest request) {
        RadioUnitResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/radio-units/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Radio Unit")
    public RadioUnitResponse update(@PathVariable UUID id, @Valid @RequestBody RadioUnitUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Radio Unit")
    public RadioUnitResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Radio Unit")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Radio Unit records")
    public BulkResult<RadioUnitResponse> bulkCreate(@Valid @RequestBody List<@Valid RadioUnitCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Radio Unit records")
    public BulkResult<RadioUnitResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Radio Unit records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Radio Unit records as CSV")
    public void export(RadioUnitSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"radio-units.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-site-id/{siteId}")
    @Operation(summary = "List Radio Unit records by Site Id")
    public PageResponse<RadioUnitResponse> bySiteId(@PathVariable UUID siteId, Pageable pageable) {
        return service.findBySiteId(siteId, pageable);
    }
}
