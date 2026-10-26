package com.uniti.cil.itta.inventory.assetmgmt.web;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.service.AssetTagService;
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
@RequestMapping("/api/v1/asset-tags")
@Tag(name = "Asset Tag", description = "Asset and Finance - Asset Tag inventory")
public class AssetTagController {

    private final AssetTagService service;

    public AssetTagController(AssetTagService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Search Asset Tag records")
    public PageResponse<AssetTagResponse> search(AssetTagSearchCriteria criteria, Pageable pageable) {
        return service.search(criteria, pageable);
    }

    @GetMapping("/summaries")
    @Operation(summary = "Pick-list of Asset Tag records")
    public List<AssetTagSummary> summaries(@RequestParam(required = false) LifecycleStatus status) {
        return service.summaries(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Asset Tag by id")
    public AssetTagResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/by-code/{code}")
    @Operation(summary = "Get a Asset Tag by business code")
    public AssetTagResponse getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/by-code/{code}")
    @Operation(summary = "Check whether a Asset Tag code exists")
    public ResponseEntity<Void> exists(@PathVariable String code) {
        return service.existsByCode(code) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a Asset Tag")
    public ResponseEntity<AssetTagResponse> create(@Valid @RequestBody AssetTagCreateRequest request) {
        AssetTagResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/asset-tags/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Asset Tag")
    public AssetTagResponse update(@PathVariable UUID id, @Valid @RequestBody AssetTagUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the lifecycle status of a Asset Tag")
    public AssetTagResponse changeStatus(@PathVariable UUID id, @RequestParam LifecycleStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Asset Tag")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create many Asset Tag records")
    public BulkResult<AssetTagResponse> bulkCreate(@Valid @RequestBody List<@Valid AssetTagCreateRequest> requests) {
        return service.bulkCreate(requests);
    }

    @PostMapping("/bulk/status")
    @Operation(summary = "Change status of many Asset Tag records")
    public BulkResult<AssetTagResponse> bulkStatus(@RequestBody List<UUID> ids, @RequestParam LifecycleStatus status) {
        return service.bulkChangeStatus(ids, status);
    }

    @GetMapping("/stats/status")
    @Operation(summary = "Counts of Asset Tag records per lifecycle status")
    public Map<LifecycleStatus, Long> statusStats() {
        return service.countByStatus();
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @Operation(summary = "Export Asset Tag records as CSV")
    public void export(AssetTagSearchCriteria criteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"asset-tags.csv\"");
        service.exportCsv(criteria, response.getWriter());
    }

    @GetMapping("/by-asset-id/{assetId}")
    @Operation(summary = "List Asset Tag records by Asset Id")
    public PageResponse<AssetTagResponse> byAssetId(@PathVariable UUID assetId, Pageable pageable) {
        return service.findByAssetId(assetId, pageable);
    }
}
