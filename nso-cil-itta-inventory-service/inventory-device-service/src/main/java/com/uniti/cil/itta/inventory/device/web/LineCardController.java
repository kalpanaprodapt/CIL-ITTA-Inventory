package com.uniti.cil.itta.inventory.device.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.service.LineCardService;
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
@RequestMapping("/api/v1/line-cards")
@Tag(name = "Line Card", description = "Network Device - Line Card inventory")
public class LineCardController {

    private final LineCardService service;

    public LineCardController(LineCardService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Line Card records")
    public PageResponse<LineCardResponse> search(LineCardSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Line Card records")
    public List<LineCardSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Line Card by id")
    public LineCardResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Line Card by business code")
    public LineCardResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Line Card code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Line Card")
    public ResponseEntity<LineCardResponse> create(@Valid @RequestBody LineCardCreateRequest request) {
        LineCardResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/line-cards/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Line Card")
    public LineCardResponse update(@PathVariable UUID id, @Valid @RequestBody LineCardUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Line Card")
    public LineCardResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Line Card")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Line Card records")
    public BulkResult<LineCardResponse> bulkCreate(@Valid @RequestBody List<@Valid LineCardCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Line Card records")
    public BulkResult<LineCardResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Line Card records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Line Card records as CSV")
    public void export(LineCardSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"line-cards.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-chassis-id/{chassisId}")
    @Operation(summary = "List Line Card records by Chassis Id")
    public PageResponse<LineCardResponse> byChassisId(@PathVariable UUID chassisId, Pageable pageable) {
        return service.findByChassisId(chassisId, pageable);
    }
}
