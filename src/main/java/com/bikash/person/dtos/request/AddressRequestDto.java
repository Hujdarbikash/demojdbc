package com.bikash.person.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequestDto {


    @NotBlank(message = "street cannot be blank")
    private String street;
    @NotBlank(message = "street cannot be blank")
    private  String city;


}
