//package com.bikash.person.repositories;
//
//import com.bikash.person.models.Address;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class AddressRepositoryTest {
//
//    @Autowired
//    private  AddressRepository addressRepository;
//
//    @Test
//    void saveAddressTest()
//    {
//
//        Address address = new Address();
//        address.setCity("Motiyahi");
//        address.setStreet("Gaighat");
//        this.addressRepository.saveAddress(address,1l);
//
//
//    }
//
//    @Test
//    @DisplayName("findAddressByEmployeeId")
//    void findAddressByEmployeeId()
//    {
//
//       this.addressRepository.getAddressByEmployeeId(1l);
//
//
//    }
//
//}