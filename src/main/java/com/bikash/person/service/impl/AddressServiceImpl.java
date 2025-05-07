package com.bikash.person.service.impl;

import com.bikash.person.dtos.request.AddressRequestDto;
import com.bikash.person.dtos.response.AddressResponseDto;
import com.bikash.person.exceptions.DatabaseOperationException;
import com.bikash.person.exceptions.ResourceNotFoundException;
import com.bikash.person.mappers.AddressMapper;
import com.bikash.person.repositories.AddressRepository;
import com.bikash.person.repositories.EmployeeRepository;
import com.bikash.person.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
private  final EmployeeRepository employeeRepository;

    public AddressServiceImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public AddressResponseDto createAddress(AddressRequestDto requestDto, long employeeId) {

        if(!this.employeeRepository.existById(employeeId))
            throw  new ResourceNotFoundException("Employee","employeeId",employeeId);

        try {
            return AddressMapper.toResponse(this.addressRepository.saveAddress(AddressMapper.toEntity(requestDto), employeeId));
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to save address in database");
        }
    }

    @Override
    public AddressResponseDto getAddressByEmployeeId(long employeeId) {

        if (!this.addressRepository.existByEmployeeId(employeeId))
            throw new ResourceNotFoundException("Address", "employeeId", employeeId);
        try {
            return AddressMapper.toResponse(this.addressRepository.getAddressByEmployeeId(employeeId));
        } catch (Exception e) {
           throw  new  DatabaseOperationException("Failed to get address ");
        }

    }
}
