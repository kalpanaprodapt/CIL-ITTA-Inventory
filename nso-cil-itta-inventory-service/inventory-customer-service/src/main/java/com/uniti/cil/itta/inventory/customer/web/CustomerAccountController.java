package com.uniti.cil.itta.inventory.customer.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.service.CustomerAccountService;
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
@RequestMapping("/api/v1/customer-accounts")
@Tag(name = "Customer Account", description = "Customers and SLA - Customer Account inventory")
public class CustomerAccountController {

    private final CustomerAccountService service;

    public CustomerAccountController(CustomerAccountService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Customer Account records")
    public PageResponse<CustomerAccountResponse> search(CustomerAccountSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Customer Account records")
    public List<CustomerAccountSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Customer Account by id")
    public CustomerAccountResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Customer Account by business code")
    public CustomerAccountResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Customer Account code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Customer Account")
    public ResponseEntity<CustomerAccountResponse> create(@Valid @RequestBody CustomerAccountCreateRequest request) {
        CustomerAccountResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/customer-accounts/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Customer Account")
    public CustomerAccountResponse update(@PathVariable UUID id, @Valid @RequestBody CustomerAccountUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Customer Account")
    public CustomerAccountResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Customer Account")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Customer Account records")
    public BulkResult<CustomerAccountResponse> bulkCreate(@Valid @RequestBody List<@Valid CustomerAccountCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Customer Account records")
    public BulkResult<CustomerAccountResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Customer Account records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Customer Account records as CSV")
    public void export(CustomerAccountSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"customer-accounts.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-customer-id/{customerId}")
    @Operation(summary = "List Customer Account records by Customer Id")
    public PageResponse<CustomerAccountResponse> byCustomerId(@PathVariable UUID customerId, Pageable pageable) {
        return service.findByCustomerId(customerId, pageable);
    }
}
