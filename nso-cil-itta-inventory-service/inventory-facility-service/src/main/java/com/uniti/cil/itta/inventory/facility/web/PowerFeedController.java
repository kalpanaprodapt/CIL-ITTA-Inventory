package com.uniti.cil.itta.inventory.facility.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.service.PowerFeedService;
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
@RequestMapping("/api/v1/power-feeds")
@Tag(name = "Power Feed", description = "Facility - Power Feed inventory")
public class PowerFeedController {

    private final PowerFeedService service;

    public PowerFeedController(PowerFeedService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Power Feed records")
    public PageResponse<PowerFeedResponse> search(PowerFeedSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Power Feed records")
    public List<PowerFeedSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Power Feed by id")
    public PowerFeedResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Power Feed by business code")
    public PowerFeedResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Power Feed code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Power Feed")
    public ResponseEntity<PowerFeedResponse> create(@Valid @RequestBody PowerFeedCreateRequest request) {
        PowerFeedResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/power-feeds/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Power Feed")
    public PowerFeedResponse update(@PathVariable UUID id, @Valid @RequestBody PowerFeedUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Power Feed")
    public PowerFeedResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Power Feed")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Power Feed records")
    public BulkResult<PowerFeedResponse> bulkCreate(@Valid @RequestBody List<@Valid PowerFeedCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Power Feed records")
    public BulkResult<PowerFeedResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Power Feed records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Power Feed records as CSV")
    public void export(PowerFeedSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"power-feeds.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-room-id/{roomId}")
    @Operation(summary = "List Power Feed records by Room Id")
    public PageResponse<PowerFeedResponse> byRoomId(@PathVariable UUID roomId, Pageable pageable) {
        return service.findByRoomId(roomId, pageable);
    }
}
