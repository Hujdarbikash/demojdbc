package com.bikash.person.controllers;

import com.bikash.person.dtos.request.AddressRequestDto;
import com.bikash.person.dtos.response.AddressResponseDto;
import com.bikash.person.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import  static  org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(AddressController.class)
class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;




    private AddressRequestDto request ;

    private AddressResponseDto response;

    private List<AddressResponseDto> responseList = new ArrayList<>();


    @BeforeEach
    void setup()
    {

        request = new AddressRequestDto();
        request.setCity("Lalitpur");
        request.setStreet("Khumaltar");

        response = new AddressResponseDto();
        response.setAddressId(100l);
        response.setCity("lalitpur");
        response.setCity("Khumaltar");

        responseList.add(response);

    }

    @AfterEach
    void tearUp()
    {
        request = null;
        responseList = null;
        response = null;
    }


    @Test
    void createAddress() throws  Exception {

        when(this.addressService.createAddress(any(AddressRequestDto.class),any(Long.class))).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/address/employee-id/{addressId}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.city").value(response.getCity()));

    }
    @Test
    void getByEmployeeId() throws  Exception {
        when(this.addressService.getAddressByEmployeeId(any(Long.class))).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/address/employee/{employeeId}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.street").value(response.getStreet()));
    }
}