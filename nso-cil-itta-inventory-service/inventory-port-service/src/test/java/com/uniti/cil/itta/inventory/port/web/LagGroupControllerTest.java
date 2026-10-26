package com.uniti.cil.itta.inventory.port.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.service.LagGroupService;
import com.uniti.cil.itta.inventory.port.support.LagGroupFixtures;
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

@WebMvcTest(LagGroupController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class LagGroupControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private LagGroupService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(LagGroupFixtures.ID)).thenReturn(LagGroupFixtures.response());
        mvc.perform(get("/api/v1/lag-groups/{id}", LagGroupFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("LAGGRO-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(LagGroupFixtures.ID)).thenThrow(new ResourceNotFoundException("LagGroup", LagGroupFixtures.ID));
        mvc.perform(get("/api/v1/lag-groups/{id}", LagGroupFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<LagGroupResponse> page = PageResponse.of(List.of(LagGroupFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/lag-groups").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(LagGroupFixtures.response());
        mvc.perform(post("/api/v1/lag-groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(LagGroupFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        LagGroupCreateRequest request = LagGroupFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/lag-groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(LagGroupFixtures.response());
        mvc.perform(put("/api/v1/lag-groups/{id}", LagGroupFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/lag-groups/{id}", LagGroupFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/lag-groups/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/lag-groups/by-code/X")).andExpect(status().isOk());
    }
}
