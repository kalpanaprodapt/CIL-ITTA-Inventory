package com.uniti.cil.itta.inventory.optical.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.service.WdmSystemService;
import com.uniti.cil.itta.inventory.optical.support.WdmSystemFixtures;
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

@WebMvcTest(WdmSystemController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class WdmSystemControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private WdmSystemService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(WdmSystemFixtures.ID)).thenReturn(WdmSystemFixtures.response());
        mvc.perform(get("/api/v1/wdm-systems/{id}", WdmSystemFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("WDMSYS-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(WdmSystemFixtures.ID)).thenThrow(new ResourceNotFoundException("WdmSystem", WdmSystemFixtures.ID));
        mvc.perform(get("/api/v1/wdm-systems/{id}", WdmSystemFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<WdmSystemResponse> page = PageResponse.of(List.of(WdmSystemFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/wdm-systems").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(WdmSystemFixtures.response());
        mvc.perform(post("/api/v1/wdm-systems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(WdmSystemFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        WdmSystemCreateRequest request = WdmSystemFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/wdm-systems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(WdmSystemFixtures.response());
        mvc.perform(put("/api/v1/wdm-systems/{id}", WdmSystemFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/wdm-systems/{id}", WdmSystemFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/wdm-systems/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/wdm-systems/by-code/X")).andExpect(status().isOk());
    }
}
