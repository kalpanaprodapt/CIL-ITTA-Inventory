package com.uniti.cil.itta.inventory.customer.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.service.ContractService;
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
@RequestMapping("/api/v1/contracts")
@Tag(name = "Contract", description = "Customers and SLA - Contract inventory")
public class ContractController {

    private final ContractService service;

    public ContractController(ContractService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Contract records")
    public PageResponse<ContractResponse> search(ContractSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Contract records")
    public List<ContractSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Contract by id")
    public ContractResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Contract by business code")
    public ContractResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Contract code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Contract")
    public ResponseEntity<ContractResponse> create(@Valid @RequestBody ContractCreateRequest request) {
        ContractResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/contracts/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Contract")
    public ContractResponse update(@PathVariable UUID id, @Valid @RequestBody ContractUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Contract")
    public ContractResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Contract")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Contract records")
    public BulkResult<ContractResponse> bulkCreate(@Valid @RequestBody List<@Valid ContractCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Contract records")
    public BulkResult<ContractResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Contract records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Contract records as CSV")
    public void export(ContractSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"contracts.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-customer-id/{customerId}")
    @Operation(summary = "List Contract records by Customer Id")
    public PageResponse<ContractResponse> byCustomerId(@PathVariable UUID customerId, Pageable pageable) {
        return service.findByCustomerId(customerId, pageable);
    }
}
