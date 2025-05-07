package com.bikash.person.repositories;

import com.bikash.person.models.Address;
import com.bikash.person.repositories.customRepositories.CustomAddressRepository;
import com.bikash.person.rowmappers.AddressRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class AddressRepository implements CustomAddressRepository {
    private  final JdbcTemplate jdbcTemplate;

    public AddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Address saveAddress(Address address, long employeeId) {

        String createAddress = "insert into address(city,street,employee_id) values(?,?,?)";
        GeneratedKeyHolder keyHolder =  new GeneratedKeyHolder();
        this.jdbcTemplate.update((connection)->{
            PreparedStatement statement = connection.prepareStatement(createAddress, new String[]{"address_id"});
            statement.setString(1,address.getCity());
            statement.setString(2,address.getStreet());
            statement.setLong(3,employeeId);
            return  statement;
        },keyHolder);

        Long addressId = keyHolder.getKey().longValue();
        address.setAddressId(addressId);
        address.setEmployeeId(employeeId);
        return address;
    }
    @Override
    public  Address getAddressByEmployeeId(long employeeId)
    {
        String sql = "select * from address a where a.employee_id = ?";
        List<Address> results = this.jdbcTemplate.query(sql, new Object[]{employeeId}, new AddressRowMapper());
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public boolean existByEmployeeId(long employeeId)  {
        String sql = "select count(*) from address a where a.employee_id = ?";
        Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class, employeeId);
        return  count>0;
    }


}
