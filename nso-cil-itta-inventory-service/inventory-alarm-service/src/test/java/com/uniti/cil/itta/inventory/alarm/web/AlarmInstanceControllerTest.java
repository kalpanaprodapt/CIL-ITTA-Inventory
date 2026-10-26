package com.uniti.cil.itta.inventory.alarm.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.service.AlarmInstanceService;
import com.uniti.cil.itta.inventory.alarm.support.AlarmInstanceFixtures;
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

@WebMvcTest(AlarmInstanceController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class AlarmInstanceControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private AlarmInstanceService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(AlarmInstanceFixtures.ID)).thenReturn(AlarmInstanceFixtures.response());
        mvc.perform(get("/api/v1/alarm-instances/{id}", AlarmInstanceFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ALARMI-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(AlarmInstanceFixtures.ID)).thenThrow(new ResourceNotFoundException("AlarmInstance", AlarmInstanceFixtures.ID));
        mvc.perform(get("/api/v1/alarm-instances/{id}", AlarmInstanceFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<AlarmInstanceResponse> page = PageResponse.of(List.of(AlarmInstanceFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/alarm-instances").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(AlarmInstanceFixtures.response());
        mvc.perform(post("/api/v1/alarm-instances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(AlarmInstanceFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        AlarmInstanceCreateRequest request = AlarmInstanceFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/alarm-instances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(AlarmInstanceFixtures.response());
        mvc.perform(put("/api/v1/alarm-instances/{id}", AlarmInstanceFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/alarm-instances/{id}", AlarmInstanceFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/alarm-instances/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/alarm-instances/by-code/X")).andExpect(status().isOk());
    }
}
