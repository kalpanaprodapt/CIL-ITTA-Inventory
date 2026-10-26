package com.uniti.cil.itta.inventory.assetmgmt.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.service.InvoiceService;
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
@RequestMapping("/api/v1/invoices")
@Tag(name = "Invoice", description = "Asset and Finance - Invoice inventory")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Invoice records")
    public PageResponse<InvoiceResponse> search(InvoiceSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Invoice records")
    public List<InvoiceSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Invoice by id")
    public InvoiceResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Invoice by business code")
    public InvoiceResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Invoice code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Invoice")
    public ResponseEntity<InvoiceResponse> create(@Valid @RequestBody InvoiceCreateRequest request) {
        InvoiceResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/invoices/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Invoice")
    public InvoiceResponse update(@PathVariable UUID id, @Valid @RequestBody InvoiceUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Invoice")
    public InvoiceResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Invoice")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Invoice records")
    public BulkResult<InvoiceResponse> bulkCreate(@Valid @RequestBody List<@Valid InvoiceCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Invoice records")
    public BulkResult<InvoiceResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Invoice records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Invoice records as CSV")
    public void export(InvoiceSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"invoices.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-po-id/{poId}")
    @Operation(summary = "List Invoice records by Po Id")
    public PageResponse<InvoiceResponse> byPoId(@PathVariable UUID poId, Pageable pageable) {
        return service.findByPoId(poId, pageable);
    }
}
