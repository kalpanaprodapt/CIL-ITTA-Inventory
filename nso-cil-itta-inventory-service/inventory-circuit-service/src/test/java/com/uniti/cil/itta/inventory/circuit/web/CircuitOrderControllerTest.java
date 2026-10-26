package com.uniti.cil.itta.inventory.circuit.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.service.CircuitOrderService;
import com.uniti.cil.itta.inventory.circuit.support.CircuitOrderFixtures;
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

@WebMvcTest(CircuitOrderController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class CircuitOrderControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private CircuitOrderService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(CircuitOrderFixtures.ID)).thenReturn(CircuitOrderFixtures.response());
        mvc.perform(get("/api/v1/circuit-orders/{id}", CircuitOrderFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CIRCUI-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(CircuitOrderFixtures.ID)).thenThrow(new ResourceNotFoundException("CircuitOrder", CircuitOrderFixtures.ID));
        mvc.perform(get("/api/v1/circuit-orders/{id}", CircuitOrderFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<CircuitOrderResponse> page = PageResponse.of(List.of(CircuitOrderFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/circuit-orders").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(CircuitOrderFixtures.response());
        mvc.perform(post("/api/v1/circuit-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(CircuitOrderFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        CircuitOrderCreateRequest request = CircuitOrderFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/circuit-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(CircuitOrderFixtures.response());
        mvc.perform(put("/api/v1/circuit-orders/{id}", CircuitOrderFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/circuit-orders/{id}", CircuitOrderFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/circuit-orders/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/circuit-orders/by-code/X")).andExpect(status().isOk());
    }
}
