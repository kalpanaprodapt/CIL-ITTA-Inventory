package com.uniti.cil.itta.inventory.port.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.service.TransceiverService;
import com.uniti.cil.itta.inventory.port.support.TransceiverFixtures;
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

@WebMvcTest(TransceiverController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class TransceiverControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private TransceiverService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(TransceiverFixtures.ID)).thenReturn(TransceiverFixtures.response());
        mvc.perform(get("/api/v1/transceivers/{id}", TransceiverFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("TRANSC-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(TransceiverFixtures.ID)).thenThrow(new ResourceNotFoundException("Transceiver", TransceiverFixtures.ID));
        mvc.perform(get("/api/v1/transceivers/{id}", TransceiverFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<TransceiverResponse> page = PageResponse.of(List.of(TransceiverFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/transceivers").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(TransceiverFixtures.response());
        mvc.perform(post("/api/v1/transceivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(TransceiverFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        TransceiverCreateRequest request = TransceiverFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/transceivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(TransceiverFixtures.response());
        mvc.perform(put("/api/v1/transceivers/{id}", TransceiverFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/transceivers/{id}", TransceiverFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/transceivers/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/transceivers/by-code/X")).andExpect(status().isOk());
    }
}
