package com.uniti.cil.itta.inventory.customer.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.exception.GlobalExceptionHandler;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.service.CustomerContactService;
import com.uniti.cil.itta.inventory.customer.support.CustomerContactFixtures;
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

@WebMvcTest(CustomerContactController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class CustomerContactControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private CustomerContactService service;

    @Test
    void getReturnsOk() throws Exception {
        when(service.get(CustomerContactFixtures.ID)).thenReturn(CustomerContactFixtures.response());
        mvc.perform(get("/api/v1/customer-contacts/{id}", CustomerContactFixtures.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CUSTOM-0001"));
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(service.get(CustomerContactFixtures.ID)).thenThrow(new ResourceNotFoundException("CustomerContact", CustomerContactFixtures.ID));
        mvc.perform(get("/api/v1/customer-contacts/{id}", CustomerContactFixtures.ID)).andExpect(status().isNotFound());
    }

    @Test
    void searchReturnsPage() throws Exception {
        PageResponse<CustomerContactResponse> page = PageResponse.of(List.of(CustomerContactFixtures.response()), 0, 20, 1);
        when(service.search(any(), any())).thenReturn(page);
        mvc.perform(get("/api/v1/customer-contacts").param("q", "one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any())).thenReturn(CustomerContactFixtures.response());
        mvc.perform(post("/api/v1/customer-contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(CustomerContactFixtures.createRequest())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createWithoutCodeReturns400() throws Exception {
        CustomerContactCreateRequest request = CustomerContactFixtures.createRequest();
        request.setCode(null);
        mvc.perform(post("/api/v1/customer-contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(service.update(any(), any())).thenReturn(CustomerContactFixtures.response());
        mvc.perform(put("/api/v1/customer-contacts/{id}", CustomerContactFixtures.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Renamed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReturns204() throws Exception {
        mvc.perform(delete("/api/v1/customer-contacts/{id}", CustomerContactFixtures.ID)).andExpect(status().isNoContent());
    }

    @Test
    void statusStatsReturnsMap() throws Exception {
        when(service.countByStatus()).thenReturn(java.util.Map.of());
        mvc.perform(get("/api/v1/customer-contacts/stats/status")).andExpect(status().isOk());
    }

    @Test
    void headChecksCodeExistence() throws Exception {
        when(service.existsByCode("X")).thenReturn(true);
        mvc.perform(head("/api/v1/customer-contacts/by-code/X")).andExpect(status().isOk());
    }
}
