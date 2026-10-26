package com.uniti.cil.itta.inventory.software.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.service.SoftwareVersionService;
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
@RequestMapping("/api/v1/software-versions")
@Tag(name = "Software Version", description = "Software and Licensing - Software Version inventory")
public class SoftwareVersionController {

    private final SoftwareVersionService service;

    public SoftwareVersionController(SoftwareVersionService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Software Version records")
    public PageResponse<SoftwareVersionResponse> search(SoftwareVersionSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Software Version records")
    public List<SoftwareVersionSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Software Version by id")
    public SoftwareVersionResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Software Version by business code")
    public SoftwareVersionResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Software Version code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Software Version")
    public ResponseEntity<SoftwareVersionResponse> create(@Valid @RequestBody SoftwareVersionCreateRequest request) {
        SoftwareVersionResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/software-versions/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Software Version")
    public SoftwareVersionResponse update(@PathVariable UUID id, @Valid @RequestBody SoftwareVersionUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Software Version")
    public SoftwareVersionResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Software Version")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Software Version records")
    public BulkResult<SoftwareVersionResponse> bulkCreate(@Valid @RequestBody List<@Valid SoftwareVersionCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Software Version records")
    public BulkResult<SoftwareVersionResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Software Version records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Software Version records as CSV")
    public void export(SoftwareVersionSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"software-versions.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-image-id/{imageId}")
    @Operation(summary = "List Software Version records by Image Id")
    public PageResponse<SoftwareVersionResponse> byImageId(@PathVariable UUID imageId, Pageable pageable) {
        return service.findByImageId(imageId, pageable);
    }
}
