package com.uniti.cil.itta.inventory.topology.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.service.TopologyEdgeService;
import com.uniti.cil.itta.inventory.topology.support.TopologyEdgeFixtures;
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

@WebMvcTest(TopologyEdgeController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class TopologyEdgeControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private TopologyEdgeService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(TopologyEdgeFixtures.ID)).thenReturn(TopologyEdgeFixtures.response());
        mvc.perform(get("/api/v1/topology-edges/{id}", TopologyEdgeFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("TOPOLO-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(TopologyEdgeFixtures.ID)).thenThrow(new ResourceNotFoundException("TopologyEdge", TopologyEdgeFixtures.ID));
        mvc.perform(get("/api/v1/topology-edges/{id}", TopologyEdgeFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<TopologyEdgeResponse> page = PageResponse.of(List.of(TopologyEdgeFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/topology-edges").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(TopologyEdgeFixtures.response());
        mvc.perform(post("/api/v1/topology-edges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(TopologyEdgeFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        TopologyEdgeCreateRequest request = TopologyEdgeFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/topology-edges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(TopologyEdgeFixtures.response());
        mvc.perform(put("/api/v1/topology-edges/{id}", TopologyEdgeFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/topology-edges/{id}", TopologyEdgeFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/topology-edges/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/topology-edges/by-code/X")).andExpect(status().isOk());
    }
}
