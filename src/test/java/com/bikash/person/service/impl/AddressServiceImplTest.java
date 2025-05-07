package com.bikash.person.service.impl;

import com.bikash.person.dtos.request.AddressRequestDto;
import com.bikash.person.dtos.response.AddressResponseDto;
import com.bikash.person.exceptions.DatabaseOperationException;
import com.bikash.person.exceptions.ResourceNotFoundException;
import com.bikash.person.mappers.AddressMapper;
import com.bikash.person.models.Address;
import com.bikash.person.repositories.AddressRepository;
import com.bikash.person.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {


    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    private AddressResponseDto response;

    private AddressRequestDto request;
    private Address address;
    private MockedStatic<AddressMapper> mockedMapper;

    @BeforeEach
    void setUo() {

        mockedMapper = mockStatic(AddressMapper.class);


        request = new AddressRequestDto();
        request.setStreet("123 main");
        request.setCity("KTM");

        address = new Address();
        address.setAddressId(100l);
        address.setEmployeeId(50l);
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());


        response = new AddressResponseDto();
        response.setCity(request.getCity());
        response.setStreet(request.getStreet());
        response.setEmployeeId(address.getEmployeeId());
        response.setAddressId(address.getAddressId());


    }

    @AfterEach
    void tearDown() {
        response = null;
        request = null;
        address =   null;
        mockedMapper.close();
    }

    @Test
    @DisplayName("CreateAddressTest")
    void createAddress() {

        mockedMapper.when(()->AddressMapper.toEntity(request)).thenReturn(address);
        mockedMapper.when(()->AddressMapper.toResponse(address)).thenReturn(response);
        when(this.employeeRepository.existById(anyLong())).thenReturn(true);

        when(this.addressRepository.saveAddress(any(Address.class),any(Long.class))).thenReturn(address);

        AddressResponseDto result = this.addressService.createAddress(request, 50l);

        verify(this.addressRepository).saveAddress(address,50l);
        assertNotNull(result);
        assertEquals(request.getCity(),result.getCity());

    }

    @Test
    @DisplayName("Create address Exceptions")
    void addressCreateTestFailure()
    {

        mockedMapper.when(()->AddressMapper.toEntity(request)).thenReturn(address);
        when(this.employeeRepository.existById(anyLong())).thenReturn(true);
        when(this.addressRepository.saveAddress(any(Address.class),any(Long.class))).thenThrow(new RuntimeException("database error "));
        assertThrows(DatabaseOperationException.class,()-> {
            this.addressService.createAddress(request, 50l);
        });


    }


    @Test
    void getAddressByEmployeeId() {

        when(this.addressRepository.existByEmployeeId(50l)).thenReturn(true);
        when(this.addressRepository.getAddressByEmployeeId(50l)).thenReturn(address);

        mockedMapper.when(()->AddressMapper.toResponse(address)).thenReturn(response);


        AddressResponseDto results = this.addressService.getAddressByEmployeeId(50l);

        assertNotNull(results);
        assertEquals(response.getCity(),results.getCity());
        verify(this.addressRepository).getAddressByEmployeeId(50l);


    }


    @Test
    void testEmployeeNotFound()
    {
        when(this.addressRepository.existByEmployeeId(50l)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,()->this.addressService.getAddressByEmployeeId(50l));


    }

    @Test
    void testDatabaseFailure()
    {
        when(this.addressRepository.existByEmployeeId(50l)).thenReturn(true);

        when(this.addressRepository.getAddressByEmployeeId(50l)).thenThrow(new RuntimeException("database error "));


        assertThrows(DatabaseOperationException.class
                ,()->this.addressService.getAddressByEmployeeId(50l));

    }



}