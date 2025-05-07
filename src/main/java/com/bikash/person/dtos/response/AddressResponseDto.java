package com.bikash.person.dtos.response;

import lombok.Data;

@Data
public class AddressResponseDto {
    private  Long addressId ;
    private String street;
    private  String city;
    private long employeeId;

}
