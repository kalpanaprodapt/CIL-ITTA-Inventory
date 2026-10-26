package com.uniti.cil.itta.inventory.ipam.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.service.IpAddressService;
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
@RequestMapping("/api/v1/ip-addresses")
@Tag(name = "Ip Address", description = "IP Management - Ip Address inventory")
public class IpAddressController {

    private final IpAddressService service;

    public IpAddressController(IpAddressService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Ip Address records")
    public PageResponse<IpAddressResponse> search(IpAddressSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Ip Address records")
    public List<IpAddressSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Ip Address by id")
    public IpAddressResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Ip Address by business code")
    public IpAddressResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Ip Address code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Ip Address")
    public ResponseEntity<IpAddressResponse> create(@Valid @RequestBody IpAddressCreateRequest request) {
        IpAddressResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/ip-addresses/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Ip Address")
    public IpAddressResponse update(@PathVariable UUID id, @Valid @RequestBody IpAddressUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Ip Address")
    public IpAddressResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Ip Address")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Ip Address records")
    public BulkResult<IpAddressResponse> bulkCreate(@Valid @RequestBody List<@Valid IpAddressCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Ip Address records")
    public BulkResult<IpAddressResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Ip Address records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Ip Address records as CSV")
    public void export(IpAddressSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"ip-addresses.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-subnet-id/{subnetId}")
    @Operation(summary = "List Ip Address records by Subnet Id")
    public PageResponse<IpAddressResponse> bySubnetId(@PathVariable UUID subnetId, Pageable pageable) {
        return service.findBySubnetId(subnetId, pageable);
    }

    @GetMapping("/by-assigned-to-id/{assignedToId}")
    @Operation(summary = "List Ip Address records by Assigned To Id")
    public PageResponse<IpAddressResponse> byAssignedToId(@PathVariable UUID assignedToId, Pageable pageable) {
        return service.findByAssignedToId(assignedToId, pageable);
    }
}
