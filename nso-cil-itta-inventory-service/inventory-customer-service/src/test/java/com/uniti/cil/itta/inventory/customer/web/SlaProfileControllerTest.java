package com.uniti.cil.itta.inventory.customer.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.service.SlaProfileService;
import com.uniti.cil.itta.inventory.customer.support.SlaProfileFixtures;
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

@WebMvcTest(SlaProfileController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class SlaProfileControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private SlaProfileService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(SlaProfileFixtures.ID)).thenReturn(SlaProfileFixtures.response());
        mvc.perform(get("/api/v1/sla-profiles/{id}", SlaProfileFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SLAPRO-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(SlaProfileFixtures.ID)).thenThrow(new ResourceNotFoundException("SlaProfile", SlaProfileFixtures.ID));
        mvc.perform(get("/api/v1/sla-profiles/{id}", SlaProfileFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<SlaProfileResponse> page = PageResponse.of(List.of(SlaProfileFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/sla-profiles").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(SlaProfileFixtures.response());
        mvc.perform(post("/api/v1/sla-profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(SlaProfileFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        SlaProfileCreateRequest request = SlaProfileFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/sla-profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(SlaProfileFixtures.response());
        mvc.perform(put("/api/v1/sla-profiles/{id}", SlaProfileFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/sla-profiles/{id}", SlaProfileFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/sla-profiles/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/sla-profiles/by-code/X")).andExpect(status().isOk());
    }
}
