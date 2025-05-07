package com.bikash.person.repositories.customRepositories;

import com.bikash.person.models.Address;

public interface CustomAddressRepository {
    Address saveAddress(Address address,long employeeId);

    Address getAddressByEmployeeId(long employeeId);

    boolean existByEmployeeId(long employeeId) throws Exception;

}
