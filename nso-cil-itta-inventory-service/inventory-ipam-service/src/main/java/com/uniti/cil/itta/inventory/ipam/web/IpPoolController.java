package com.uniti.cil.itta.inventory.ipam.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.service.IpPoolService;
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
@RequestMapping("/api/v1/ip-pools")
@Tag(name = "Ip Pool", description = "IP Management - Ip Pool inventory")
public class IpPoolController {

    private final IpPoolService service;

    public IpPoolController(IpPoolService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Ip Pool records")
    public PageResponse<IpPoolResponse> search(IpPoolSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Ip Pool records")
    public List<IpPoolSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Ip Pool by id")
    public IpPoolResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Ip Pool by business code")
    public IpPoolResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Ip Pool code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Ip Pool")
    public ResponseEntity<IpPoolResponse> create(@Valid @RequestBody IpPoolCreateRequest request) {
        IpPoolResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/ip-pools/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Ip Pool")
    public IpPoolResponse update(@PathVariable UUID id, @Valid @RequestBody IpPoolUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Ip Pool")
    public IpPoolResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Ip Pool")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Ip Pool records")
    public BulkResult<IpPoolResponse> bulkCreate(@Valid @RequestBody List<@Valid IpPoolCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Ip Pool records")
    public BulkResult<IpPoolResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Ip Pool records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Ip Pool records as CSV")
    public void export(IpPoolSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"ip-pools.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-subnet-id/{subnetId}")
    @Operation(summary = "List Ip Pool records by Subnet Id")
    public PageResponse<IpPoolResponse> bySubnetId(@PathVariable UUID subnetId, Pageable pageable) {
        return service.findBySubnetId(subnetId, pageable);
    }
}
