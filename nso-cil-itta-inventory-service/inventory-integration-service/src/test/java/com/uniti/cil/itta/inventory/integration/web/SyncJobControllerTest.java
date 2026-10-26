package com.uniti.cil.itta.inventory.integration.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.service.SyncJobService;
import com.uniti.cil.itta.inventory.integration.support.SyncJobFixtures;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SyncJobController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class SyncJobControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private SyncJobService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(SyncJobFixtures.ID)).thenReturn(SyncJobFixtures.response());
        mvc.perform(get("/api/v1/sync-jobs/{id}", SyncJobFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SYNCJO-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(SyncJobFixtures.ID)).thenThrow(new ResourceNotFoundException("SyncJob", SyncJobFixtures.ID));
        mvc.perform(get("/api/v1/sync-jobs/{id}", SyncJobFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<SyncJobResponse> page = PageResponse.of(List.of(SyncJobFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/sync-jobs").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(SyncJobFixtures.response());
        mvc.perform(post("/api/v1/sync-jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(SyncJobFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        SyncJobCreateRequest request = SyncJobFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/sync-jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(SyncJobFixtures.response());
        mvc.perform(put("/api/v1/sync-jobs/{id}", SyncJobFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/sync-jobs/{id}", SyncJobFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/sync-jobs/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/sync-jobs/by-code/X")).andExpect(status().isOk());
    }
}
