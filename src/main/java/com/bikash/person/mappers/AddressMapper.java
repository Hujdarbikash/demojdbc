package com.bikash.person.mappers;

import com.bikash.person.dtos.request.AddressRequestDto;
import com.bikash.person.dtos.response.AddressResponseDto;
import com.bikash.person.models.Address;

public class AddressMapper {
    public static Address toEntity(AddressRequestDto addressRequestDto)
    {
        Address address = new Address();
        address.setStreet(addressRequestDto.getStreet());
        address.setCity(addressRequestDto.getCity());
        return  address;
    }

    public  static AddressResponseDto toResponse(Address address)
    {
        AddressResponseDto response = new AddressResponseDto();
        if(address!=null){
            response.setAddressId(address.getAddressId());
            response.setCity(address.getCity());
            response.setStreet(address.getStreet());
            response.setEmployeeId(address.getEmployeeId());
        }
        return  response;
    }
}
