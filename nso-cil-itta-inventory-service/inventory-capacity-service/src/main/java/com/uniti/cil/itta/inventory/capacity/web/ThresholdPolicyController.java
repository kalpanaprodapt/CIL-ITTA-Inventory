package com.uniti.cil.itta.inventory.capacity.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.service.ThresholdPolicyService;
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
@RequestMapping("/api/v1/threshold-policies")
@Tag(name = "Threshold Policy", description = "Capacity Planning - Threshold Policy inventory")
public class ThresholdPolicyController {

    private final ThresholdPolicyService service;

    public ThresholdPolicyController(ThresholdPolicyService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Threshold Policy records")
    public PageResponse<ThresholdPolicyResponse> search(ThresholdPolicySearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Threshold Policy records")
    public List<ThresholdPolicySummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Threshold Policy by id")
    public ThresholdPolicyResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Threshold Policy by business code")
    public ThresholdPolicyResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Threshold Policy code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Threshold Policy")
    public ResponseEntity<ThresholdPolicyResponse> create(@Valid @RequestBody ThresholdPolicyCreateRequest request) {
        ThresholdPolicyResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/threshold-policies/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Threshold Policy")
    public ThresholdPolicyResponse update(@PathVariable UUID id, @Valid @RequestBody ThresholdPolicyUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Threshold Policy")
    public ThresholdPolicyResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Threshold Policy")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Threshold Policy records")
    public BulkResult<ThresholdPolicyResponse> bulkCreate(@Valid @RequestBody List<@Valid ThresholdPolicyCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Threshold Policy records")
    public BulkResult<ThresholdPolicyResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Threshold Policy records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Threshold Policy records as CSV")
    public void export(ThresholdPolicySearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"threshold-policies.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }
}
