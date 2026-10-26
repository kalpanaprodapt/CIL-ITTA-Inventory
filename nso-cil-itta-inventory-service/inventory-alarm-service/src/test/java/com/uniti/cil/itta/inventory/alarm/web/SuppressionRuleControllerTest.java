package com.uniti.cil.itta.inventory.alarm.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.service.SuppressionRuleService;
import com.uniti.cil.itta.inventory.alarm.support.SuppressionRuleFixtures;
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

@WebMvcTest(SuppressionRuleController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class SuppressionRuleControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private SuppressionRuleService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(SuppressionRuleFixtures.ID)).thenReturn(SuppressionRuleFixtures.response());
        mvc.perform(get("/api/v1/suppression-rules/{id}", SuppressionRuleFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUPPRE-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(SuppressionRuleFixtures.ID)).thenThrow(new ResourceNotFoundException("SuppressionRule", SuppressionRuleFixtures.ID));
        mvc.perform(get("/api/v1/suppression-rules/{id}", SuppressionRuleFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<SuppressionRuleResponse> page = PageResponse.of(List.of(SuppressionRuleFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/suppression-rules").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(SuppressionRuleFixtures.response());
        mvc.perform(post("/api/v1/suppression-rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(SuppressionRuleFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        SuppressionRuleCreateRequest request = SuppressionRuleFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/suppression-rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(SuppressionRuleFixtures.response());
        mvc.perform(put("/api/v1/suppression-rules/{id}", SuppressionRuleFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/suppression-rules/{id}", SuppressionRuleFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/suppression-rules/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/suppression-rules/by-code/X")).andExpect(status().isOk());
    }
}
