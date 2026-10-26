package com.uniti.cil.itta.inventory.geography.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Country records. */
public interface CountryService {

    CountryResponse get(UUID id);

    CountryResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CountryResponse> search(CountrySearchCriteria criteria, Pageable pageable);

    List<CountrySummary> summaries(LifecycleStatus status);

    CountryResponse create(CountryCreateRequest request);

    CountryResponse update(UUID id, CountryUpdateRequest request);

    CountryResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CountryResponse> bulkCreate(List<CountryCreateRequest> requests);

    BulkResult<CountryResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CountrySearchCriteria criteria, Writer writer);

}
