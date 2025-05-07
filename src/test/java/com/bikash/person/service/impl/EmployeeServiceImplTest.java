package com.bikash.person.service.impl;

import com.bikash.person.dtos.request.EmployeeRequestDto;
import com.bikash.person.dtos.response.DepartmentResponseDto;
import com.bikash.person.dtos.response.EmployeeResponseDto;
import com.bikash.person.exceptions.DatabaseOperationException;
import com.bikash.person.exceptions.ResourceNotFoundException;
import com.bikash.person.mappers.EmployeeMapper;
import com.bikash.person.models.Address;
import com.bikash.person.models.Department;
import com.bikash.person.models.Employee;
import com.bikash.person.models.Post;
import com.bikash.person.repositories.DepartmentRepository;
import com.bikash.person.repositories.EmployeeRepository;
import com.bikash.person.service.EmailService;
import com.bikash.person.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {


    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @Mock
    private EmployeeRepository employeeRepository;


    @Mock
    private EmailService emailService;
    @Mock
    private DepartmentRepository departmentRepository;
    private EmployeeRequestDto request;
    private Department department;

    private Employee employee;
    private EmployeeResponseDto response;

    private List<EmployeeRequestDto> employeeRequestDtoLIst = new ArrayList<>();

    private List<Employee> employeeList = new ArrayList<>();


    private MockedStatic<EmployeeMapper> mockedEmployeeMapper;

    private DepartmentResponseDto departmentResponseDto;

    private Post post;

    private  Address address;

    @BeforeEach
    void setUp() {

        mockedEmployeeMapper = mockStatic(EmployeeMapper.class);



        address = new Address();
        address.setAddressId(100l);
        address.setEmployeeId(50l);
        address.setStreet("123 street");
        address.setCity("HONK");


        post = new Post();
        post = new Post();
        post.setPostId(1l);
        post.setEmployeeId(1l);
        post.setContent("Test");

        List<Post>posts = new ArrayList<>();
        posts.add(post);

        department = new Department();
        department.setDepartmentId(1l);
        department.setDepartmentName("IT");



        employee = new Employee();
        employee.setEmployeeId(1l);
        employee.setDepartment(department);
        employee.setPosts(posts);
        employee.setName("Bharat");
        employee.setEmail("hujdarbikash000@gmail.com");

        employeeList.add(employee);


        request = new EmployeeRequestDto();
        request.setName("Bharat");
        request.setDepartment(department);


        departmentResponseDto = new DepartmentResponseDto();
        departmentResponseDto.setDepartmentName(request.getDepartment().getDepartmentName());
        departmentResponseDto.setDepartmentId(request.getDepartment().getDepartmentId());


        response = new EmployeeResponseDto();
        response.setName(request.getName());
        response.setDepartment(departmentResponseDto);
        response.setEmployeeId(employee.getEmployeeId());
        response.setPosts( posts);;
        response.setEmail(request.getEmail());




    }

    @AfterEach
    void tearDown() {
        mockedEmployeeMapper.close();
        response= null;

    }

    @Test
    void createEmployee() {

        mockedEmployeeMapper.when(()->EmployeeMapper.toEntity(request)).thenReturn(employee);
        mockedEmployeeMapper.when(()->EmployeeMapper.toResponse(employee)).thenReturn(response);


        when(this.employeeRepository.existByEmail(any())).thenReturn(false);

        when(this.departmentRepository.existById(anyLong())).thenReturn(true);
        doNothing().when(this.emailService).sendEmailWithTemplate(any(),any(),any(),any(),any());

        when(this.employeeRepository.createEmployee(any(Employee.class),anyLong())).thenReturn(employee);

        EmployeeResponseDto result= this.employeeService.createEmployee(request);
        assertNotNull(result);
        assertEquals(employee.getName(),result.getName());


    }


    @Test
    void createEmployeeFailure() {

        mockedEmployeeMapper.when(()->EmployeeMapper.toEntity(request)).thenReturn(employee);
        mockedEmployeeMapper.when(()->EmployeeMapper.toResponse(employee)).thenReturn(response);


        when(this.employeeRepository.existByEmail(any())).thenReturn(false);

        when(this.departmentRepository.existById(anyLong())).thenReturn(true);

        when(this.employeeRepository.createEmployee(employee,request.getDepartment().getDepartmentId())).thenThrow(new RuntimeException());

        assertThrows(DatabaseOperationException.class,()->this.employeeService.createEmployee(request));

    }

    @Test
    void getEmployeeById() {


        mockedEmployeeMapper.when(()->EmployeeMapper.toResponse(employee)).thenReturn(response);

        when(this.employeeRepository.getEmployeeById(anyLong())).thenReturn(employee);

        EmployeeResponseDto result = this.employeeService.getEmployeeById(1l);
        assertEquals(employee.getName(),result.getName());

    }

    @Test
    void getEmployeeByIdNotFoundTest() {


        mockedEmployeeMapper.when(()->EmployeeMapper.toResponse(employee)).thenReturn(response);

        when(this.employeeRepository.getEmployeeById(anyLong())).thenThrow(new RuntimeException());

        assertThrows(ResourceNotFoundException.class,()->this.employeeService.getEmployeeById(1l));

    }

    @Test
    void getAllEmployee() {


        mockedEmployeeMapper.when(()->EmployeeMapper.toResponse(employee)).thenReturn(response);

        when(this.employeeRepository.getAllEmployee()).thenReturn(employeeList);

        List<EmployeeResponseDto> allEmployee = this.employeeService.getAllEmployee();
        assertNotNull(allEmployee);
        assertEquals(employeeList.size(),allEmployee.size());



    }
    @Test
    void getAllEmployeeFailure() {

        mockedEmployeeMapper.when(()->EmployeeMapper.toResponse(employee)).thenReturn(response);

        when(this.employeeRepository.getAllEmployee()).thenThrow(new RuntimeException());

        assertThrows(DatabaseOperationException.class,()->this.employeeService.getAllEmployee());

    }

    @Test
    void updateEmployee() {


        when(this.employeeRepository.existById(anyLong())).thenReturn(true);

        mockedEmployeeMapper.when(()->EmployeeMapper.toEntity(request)).thenReturn(employee);
        mockedEmployeeMapper.when(()->EmployeeMapper.toResponse(employee)).thenReturn(response);

        when(this.employeeRepository.updateEmployee(any(Employee.class),anyLong())).thenReturn(employee);

        EmployeeResponseDto result= this.employeeService.updateEmployee(request,1l);
        assertNotNull(result);
        assertEquals(employee.getName(),result.getName());
    }

    @Test
    void updateEmployeeFailure() {

        when(this.employeeRepository.existById(anyLong())).thenReturn(true);

        mockedEmployeeMapper.when(()->EmployeeMapper.toEntity(request)).thenReturn(employee);
        mockedEmployeeMapper.when(()->EmployeeMapper.toResponse(employee)).thenReturn(response);

        when(this.employeeRepository.updateEmployee(any(Employee.class),anyLong())).thenThrow(new RuntimeException());

        assertThrows(DatabaseOperationException.class,()->this.employeeService.updateEmployee(request,1l));

    }


    @Test
    void updateEmployeeNotFoundFailure() {

        when(this.employeeRepository.existById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,()->this.employeeService.updateEmployee(request,1l));
    }
    @Test
    void deleteEmployee() {
        when(this.employeeRepository.existById(anyLong())).thenReturn(true);
        doNothing().when(this.employeeRepository).deleteEmployee(anyLong());

        this.employeeService.deleteEmployee(1l);
        verify(this.employeeRepository).deleteEmployee(1l);



    }

    @Test
    void deleteEmployeeNotFound() {
        when(this.employeeRepository.existById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,()->this.employeeService.deleteEmployee(1l));

    }

    @Test
    void deleteEmployeeFailure() {
        when(this.employeeRepository.existById(anyLong())).thenReturn(true);

        doThrow(new RuntimeException("db Failed")).when(this.employeeRepository).deleteEmployee(anyLong());

        assertThrows(DatabaseOperationException.class,()->this.employeeService.deleteEmployee(1l));


    }


}