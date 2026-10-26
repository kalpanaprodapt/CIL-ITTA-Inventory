package com.uniti.cil.itta.inventory.port.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.service.InterfaceService;
import com.uniti.cil.itta.inventory.port.support.InterfaceFixtures;
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

@WebMvcTest(InterfaceController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class InterfaceControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private InterfaceService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(InterfaceFixtures.ID)).thenReturn(InterfaceFixtures.response());
        mvc.perform(get("/api/v1/interfaces/{id}", InterfaceFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("INTERF-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(InterfaceFixtures.ID)).thenThrow(new ResourceNotFoundException("Interface", InterfaceFixtures.ID));
        mvc.perform(get("/api/v1/interfaces/{id}", InterfaceFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<InterfaceResponse> page = PageResponse.of(List.of(InterfaceFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/interfaces").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(InterfaceFixtures.response());
        mvc.perform(post("/api/v1/interfaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(InterfaceFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        InterfaceCreateRequest request = InterfaceFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/interfaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(InterfaceFixtures.response());
        mvc.perform(put("/api/v1/interfaces/{id}", InterfaceFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/interfaces/{id}", InterfaceFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/interfaces/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/interfaces/by-code/X")).andExpect(status().isOk());
    }
}
