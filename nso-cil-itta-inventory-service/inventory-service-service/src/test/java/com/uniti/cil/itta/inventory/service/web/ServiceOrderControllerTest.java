package com.uniti.cil.itta.inventory.service.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.service.ServiceOrderService;
import com.uniti.cil.itta.inventory.service.support.ServiceOrderFixtures;
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

@WebMvcTest(ServiceOrderController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class ServiceOrderControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private ServiceOrderService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(ServiceOrderFixtures.ID)).thenReturn(ServiceOrderFixtures.response());
        mvc.perform(get("/api/v1/service-orders/{id}", ServiceOrderFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SERVIC-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(ServiceOrderFixtures.ID)).thenThrow(new ResourceNotFoundException("ServiceOrder", ServiceOrderFixtures.ID));
        mvc.perform(get("/api/v1/service-orders/{id}", ServiceOrderFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<ServiceOrderResponse> page = PageResponse.of(List.of(ServiceOrderFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/service-orders").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(ServiceOrderFixtures.response());
        mvc.perform(post("/api/v1/service-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(ServiceOrderFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        ServiceOrderCreateRequest request = ServiceOrderFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/service-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(ServiceOrderFixtures.response());
        mvc.perform(put("/api/v1/service-orders/{id}", ServiceOrderFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/service-orders/{id}", ServiceOrderFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/service-orders/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/service-orders/by-code/X")).andExpect(status().isOk());
    }
}
