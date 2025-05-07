package com.bikash.person.controllers;

import com.bikash.person.dtos.request.AddressRequestDto;
import com.bikash.person.dtos.response.AddressResponseDto;
import com.bikash.person.globalresponse.RestResponse;
import com.bikash.person.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/address")
public class AddressController {
    private final  AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/employee-id/{employeeId}")
    public ResponseEntity<?> createAddress( @Valid  @RequestBody AddressRequestDto requestDto,
                                           @PathVariable("employeeId") long employeeId){
        AddressResponseDto address = this.addressService.createAddress(requestDto, employeeId);
        return new RestResponse<>().createdWithPayload(address,"Address created Successfully");
    }

    @GetMapping("/employee/{employeeId}")
    public  ResponseEntity<?> getByEmployeeId(@PathVariable("employeeId") long employeeId)
    {
        return  new RestResponse<>().okWithPayload(this.addressService.getAddressByEmployeeId(employeeId),"Address fetched successfully");
    }




}
