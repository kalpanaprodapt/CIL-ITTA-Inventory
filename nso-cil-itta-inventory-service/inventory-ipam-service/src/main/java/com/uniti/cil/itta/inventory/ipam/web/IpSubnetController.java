package com.uniti.cil.itta.inventory.ipam.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.service.IpSubnetService;
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
@RequestMapping("/api/v1/ip-subnets")
@Tag(name = "Ip Subnet", description = "IP Management - Ip Subnet inventory")
public class IpSubnetController {

    private final IpSubnetService service;

    public IpSubnetController(IpSubnetService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Ip Subnet records")
    public PageResponse<IpSubnetResponse> search(IpSubnetSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Ip Subnet records")
    public List<IpSubnetSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Ip Subnet by id")
    public IpSubnetResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Ip Subnet by business code")
    public IpSubnetResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Ip Subnet code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Ip Subnet")
    public ResponseEntity<IpSubnetResponse> create(@Valid @RequestBody IpSubnetCreateRequest request) {
        IpSubnetResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/ip-subnets/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Ip Subnet")
    public IpSubnetResponse update(@PathVariable UUID id, @Valid @RequestBody IpSubnetUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Ip Subnet")
    public IpSubnetResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Ip Subnet")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Ip Subnet records")
    public BulkResult<IpSubnetResponse> bulkCreate(@Valid @RequestBody List<@Valid IpSubnetCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Ip Subnet records")
    public BulkResult<IpSubnetResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Ip Subnet records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Ip Subnet records as CSV")
    public void export(IpSubnetSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"ip-subnets.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-vrf-id/{vrfId}")
    @Operation(summary = "List Ip Subnet records by Vrf Id")
    public PageResponse<IpSubnetResponse> byVrfId(@PathVariable UUID vrfId, Pageable pageable) {
        return service.findByVrfId(vrfId, pageable);
    }

    @GetMapping("/by-vlan-id/{vlanId}")
    @Operation(summary = "List Ip Subnet records by Vlan Id")
    public PageResponse<IpSubnetResponse> byVlanId(@PathVariable UUID vlanId, Pageable pageable) {
        return service.findByVlanId(vlanId, pageable);
    }
}
