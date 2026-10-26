package com.uniti.cil.itta.inventory.customer.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.service.CustomerContactService;
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
@RequestMapping("/api/v1/customer-contacts")
@Tag(name = "Customer Contact", description = "Customers and SLA - Customer Contact inventory")
public class CustomerContactController {

    private final CustomerContactService service;

    public CustomerContactController(CustomerContactService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Customer Contact records")
    public PageResponse<CustomerContactResponse> search(CustomerContactSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Customer Contact records")
    public List<CustomerContactSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Customer Contact by id")
    public CustomerContactResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Customer Contact by business code")
    public CustomerContactResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Customer Contact code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Customer Contact")
    public ResponseEntity<CustomerContactResponse> create(@Valid @RequestBody CustomerContactCreateRequest request) {
        CustomerContactResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/customer-contacts/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Customer Contact")
    public CustomerContactResponse update(@PathVariable UUID id, @Valid @RequestBody CustomerContactUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Customer Contact")
    public CustomerContactResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Customer Contact")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Customer Contact records")
    public BulkResult<CustomerContactResponse> bulkCreate(@Valid @RequestBody List<@Valid CustomerContactCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Customer Contact records")
    public BulkResult<CustomerContactResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Customer Contact records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Customer Contact records as CSV")
    public void export(CustomerContactSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"customer-contacts.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-customer-id/{customerId}")
    @Operation(summary = "List Customer Contact records by Customer Id")
    public PageResponse<CustomerContactResponse> byCustomerId(@PathVariable UUID customerId, Pageable pageable) {
        return service.findByCustomerId(customerId, pageable);
    }
}
