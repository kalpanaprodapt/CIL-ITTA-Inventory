package com.uniti.cil.itta.inventory.ipam.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.service.VrfInstanceService;
import com.uniti.cil.itta.inventory.ipam.support.VrfInstanceFixtures;
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

@WebMvcTest(VrfInstanceController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class VrfInstanceControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private VrfInstanceService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(VrfInstanceFixtures.ID)).thenReturn(VrfInstanceFixtures.response());
        mvc.perform(get("/api/v1/vrf-instances/{id}", VrfInstanceFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("VRFINS-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(VrfInstanceFixtures.ID)).thenThrow(new ResourceNotFoundException("VrfInstance", VrfInstanceFixtures.ID));
        mvc.perform(get("/api/v1/vrf-instances/{id}", VrfInstanceFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<VrfInstanceResponse> page = PageResponse.of(List.of(VrfInstanceFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/vrf-instances").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(VrfInstanceFixtures.response());
        mvc.perform(post("/api/v1/vrf-instances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(VrfInstanceFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        VrfInstanceCreateRequest request = VrfInstanceFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/vrf-instances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(VrfInstanceFixtures.response());
        mvc.perform(put("/api/v1/vrf-instances/{id}", VrfInstanceFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/vrf-instances/{id}", VrfInstanceFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/vrf-instances/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/vrf-instances/by-code/X")).andExpect(status().isOk());
    }
}
