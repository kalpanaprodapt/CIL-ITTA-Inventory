package com.uniti.cil.itta.inventory.switching.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.service.OspfAreaService;
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
@RequestMapping("/api/v1/ospf-areas")
@Tag(name = "Ospf Area", description = "L2 and L3 Routing - Ospf Area inventory")
public class OspfAreaController {

    private final OspfAreaService service;

    public OspfAreaController(OspfAreaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Ospf Area records")
    public PageResponse<OspfAreaResponse> search(OspfAreaSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Ospf Area records")
    public List<OspfAreaSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Ospf Area by id")
    public OspfAreaResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Ospf Area by business code")
    public OspfAreaResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Ospf Area code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Ospf Area")
    public ResponseEntity<OspfAreaResponse> create(@Valid @RequestBody OspfAreaCreateRequest request) {
        OspfAreaResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/ospf-areas/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Ospf Area")
    public OspfAreaResponse update(@PathVariable UUID id, @Valid @RequestBody OspfAreaUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Ospf Area")
    public OspfAreaResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Ospf Area")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Ospf Area records")
    public BulkResult<OspfAreaResponse> bulkCreate(@Valid @RequestBody List<@Valid OspfAreaCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Ospf Area records")
    public BulkResult<OspfAreaResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Ospf Area records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Ospf Area records as CSV")
    public void export(OspfAreaSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"ospf-areas.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }
}
