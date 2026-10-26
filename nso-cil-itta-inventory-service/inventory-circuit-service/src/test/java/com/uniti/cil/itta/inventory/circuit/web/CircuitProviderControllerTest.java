package com.uniti.cil.itta.inventory.circuit.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.service.CircuitProviderService;
import com.uniti.cil.itta.inventory.circuit.support.CircuitProviderFixtures;
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

@WebMvcTest(CircuitProviderController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class CircuitProviderControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private CircuitProviderService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(CircuitProviderFixtures.ID)).thenReturn(CircuitProviderFixtures.response());
        mvc.perform(get("/api/v1/circuit-providers/{id}", CircuitProviderFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CIRCUI-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(CircuitProviderFixtures.ID)).thenThrow(new ResourceNotFoundException("CircuitProvider", CircuitProviderFixtures.ID));
        mvc.perform(get("/api/v1/circuit-providers/{id}", CircuitProviderFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<CircuitProviderResponse> page = PageResponse.of(List.of(CircuitProviderFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/circuit-providers").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(CircuitProviderFixtures.response());
        mvc.perform(post("/api/v1/circuit-providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(CircuitProviderFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        CircuitProviderCreateRequest request = CircuitProviderFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/circuit-providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(CircuitProviderFixtures.response());
        mvc.perform(put("/api/v1/circuit-providers/{id}", CircuitProviderFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/circuit-providers/{id}", CircuitProviderFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/circuit-providers/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/circuit-providers/by-code/X")).andExpect(status().isOk());
    }
}
