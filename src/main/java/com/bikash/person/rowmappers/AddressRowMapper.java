package com.bikash.person.rowmappers;

import com.bikash.person.models.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
            Address address = new Address();
            address.setAddressId(rs.getLong("address_id"));
            address.setEmployeeId(rs.getLong("employee_id"));
            address.setCity(rs.getString("city"));
            address.setStreet(rs.getString("street"));
            return address;
        }
    }

