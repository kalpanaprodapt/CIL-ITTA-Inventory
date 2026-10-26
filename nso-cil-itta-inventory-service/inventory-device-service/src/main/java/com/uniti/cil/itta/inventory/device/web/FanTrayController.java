package com.uniti.cil.itta.inventory.device.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.service.FanTrayService;
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
@RequestMapping("/api/v1/fan-traies")
@Tag(name = "Fan Tray", description = "Network Device - Fan Tray inventory")
public class FanTrayController {

    private final FanTrayService service;

    public FanTrayController(FanTrayService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Fan Tray records")
    public PageResponse<FanTrayResponse> search(FanTraySearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Fan Tray records")
    public List<FanTraySummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Fan Tray by id")
    public FanTrayResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Fan Tray by business code")
    public FanTrayResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Fan Tray code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Fan Tray")
    public ResponseEntity<FanTrayResponse> create(@Valid @RequestBody FanTrayCreateRequest request) {
        FanTrayResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/fan-traies/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Fan Tray")
    public FanTrayResponse update(@PathVariable UUID id, @Valid @RequestBody FanTrayUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Fan Tray")
    public FanTrayResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Fan Tray")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Fan Tray records")
    public BulkResult<FanTrayResponse> bulkCreate(@Valid @RequestBody List<@Valid FanTrayCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Fan Tray records")
    public BulkResult<FanTrayResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Fan Tray records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Fan Tray records as CSV")
    public void export(FanTraySearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"fan-traies.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-chassis-id/{chassisId}")
    @Operation(summary = "List Fan Tray records by Chassis Id")
    public PageResponse<FanTrayResponse> byChassisId(@PathVariable UUID chassisId, Pageable pageable) {
        return service.findByChassisId(chassisId, pageable);
    }
}
