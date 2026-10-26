package com.uniti.cil.itta.inventory.change.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.service.MaintenanceWindowService;
import com.uniti.cil.itta.inventory.change.support.MaintenanceWindowFixtures;
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

@WebMvcTest(MaintenanceWindowController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class MaintenanceWindowControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private MaintenanceWindowService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(MaintenanceWindowFixtures.ID)).thenReturn(MaintenanceWindowFixtures.response());
        mvc.perform(get("/api/v1/maintenance-windows/{id}", MaintenanceWindowFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("MAINTE-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(MaintenanceWindowFixtures.ID)).thenThrow(new ResourceNotFoundException("MaintenanceWindow", MaintenanceWindowFixtures.ID));
        mvc.perform(get("/api/v1/maintenance-windows/{id}", MaintenanceWindowFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<MaintenanceWindowResponse> page = PageResponse.of(List.of(MaintenanceWindowFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/maintenance-windows").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(MaintenanceWindowFixtures.response());
        mvc.perform(post("/api/v1/maintenance-windows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(MaintenanceWindowFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        MaintenanceWindowCreateRequest request = MaintenanceWindowFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/maintenance-windows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(MaintenanceWindowFixtures.response());
        mvc.perform(put("/api/v1/maintenance-windows/{id}", MaintenanceWindowFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/maintenance-windows/{id}", MaintenanceWindowFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/maintenance-windows/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/maintenance-windows/by-code/X")).andExpect(status().isOk());
    }
}
