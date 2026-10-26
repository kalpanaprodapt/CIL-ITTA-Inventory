package com.uniti.cil.itta.inventory.mpls.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.service.VpnServiceService;
import com.uniti.cil.itta.inventory.mpls.support.VpnServiceFixtures;
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

@WebMvcTest(VpnServiceController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class VpnServiceControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private VpnServiceService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(VpnServiceFixtures.ID)).thenReturn(VpnServiceFixtures.response());
        mvc.perform(get("/api/v1/vpn-services/{id}", VpnServiceFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("VPNSER-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(VpnServiceFixtures.ID)).thenThrow(new ResourceNotFoundException("VpnService", VpnServiceFixtures.ID));
        mvc.perform(get("/api/v1/vpn-services/{id}", VpnServiceFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<VpnServiceResponse> page = PageResponse.of(List.of(VpnServiceFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/vpn-services").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(VpnServiceFixtures.response());
        mvc.perform(post("/api/v1/vpn-services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(VpnServiceFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        VpnServiceCreateRequest request = VpnServiceFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/vpn-services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(VpnServiceFixtures.response());
        mvc.perform(put("/api/v1/vpn-services/{id}", VpnServiceFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/vpn-services/{id}", VpnServiceFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/vpn-services/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/vpn-services/by-code/X")).andExpect(status().isOk());
    }
}
