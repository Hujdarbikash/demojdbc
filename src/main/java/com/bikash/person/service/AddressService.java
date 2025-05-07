package com.bikash.person.service;

import com.bikash.person.dtos.request.AddressRequestDto;
import com.bikash.person.dtos.response.AddressResponseDto;

public interface AddressService {
    public AddressResponseDto createAddress(AddressRequestDto requestDto, long employeeId);
    public  AddressResponseDto getAddressByEmployeeId(long employeeId);
}
