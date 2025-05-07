package com.bikash.person.models;

import lombok.*;
import org.springframework.context.annotation.Profile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Address {

    private  Long addressId;
    private  String street;
    private  String city;

    private  long employeeId;

}
