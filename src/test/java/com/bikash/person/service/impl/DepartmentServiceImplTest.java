package com.bikash.person.service.impl;

import com.bikash.person.dtos.request.DepartmentRequestDto;
import com.bikash.person.dtos.response.DepartmentResponseDto;
import com.bikash.person.exceptions.DatabaseOperationException;
import com.bikash.person.exceptions.ResourceNotFoundException;
import com.bikash.person.mappers.DepartmentMapper;
import com.bikash.person.models.Department;
import com.bikash.person.repositories.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {


    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    private DepartmentRequestDto request;

    private Department department;

    private DepartmentResponseDto response;
    private MockedStatic<DepartmentMapper> mockedDepartmentMapper;


    private List<Department> departmentList = new ArrayList<>();


    @BeforeEach
    void setUp() {

        mockedDepartmentMapper = mockStatic(DepartmentMapper.class);

        request = new DepartmentRequestDto();
        request.setDepartmentName("ANFA");


        response = new DepartmentResponseDto();
        response.setDepartmentId(100l);
        response.setDepartmentName(request.getDepartmentName());


        department = new Department();
        department.setDepartmentId(response.getDepartmentId());
        department.setDepartmentName(response.getDepartmentName());


        departmentList.add(department);

        departmentList.add(department);


    }

    @AfterEach
    void tearUp() {
        mockedDepartmentMapper.close();

    }


    @Test
    void createDepartment() {

        mockedDepartmentMapper.when(() -> DepartmentMapper.toEntity(any(DepartmentRequestDto.class))).thenReturn(department);

        mockedDepartmentMapper.when(() -> DepartmentMapper.toResponse(any(Department.class))).thenReturn(response);

        when(this.departmentRepository.createDepartment(any(Department.class))).thenReturn(department);


        DepartmentResponseDto result = this.departmentService.createDepartment(request);
        assertEquals(response.getDepartmentId(), result.getDepartmentId());
        assertNotNull(result);
        verify(this.departmentRepository).createDepartment(any(Department.class));

    }

    @Test
    void testCreateDepartmentFailureException() {
        mockedDepartmentMapper.when(() -> DepartmentMapper.toEntity(request)).thenReturn(department);

        when(this.departmentRepository.createDepartment(any(Department.class))).thenThrow(new RuntimeException("Failed"));

        assertThrows(DatabaseOperationException.class, () -> this.departmentService.createDepartment(request));

    }


    @Test
    void getDepartmentById() {


        mockedDepartmentMapper.when(() -> DepartmentMapper.toResponse(department)).thenReturn(response);

        when(this.departmentRepository.getDepartmentById(anyLong())).thenReturn(department);

        DepartmentResponseDto result = this.departmentService.getDepartmentById(100l);

        assertEquals(response.getDepartmentId(), result.getDepartmentId());
        assertNotNull(result);
        verify(this.departmentRepository).getDepartmentById(anyLong());


    }

    @Test
    void getDepartmentByIdFailureTest() {

        when(this.departmentRepository.getDepartmentById(anyLong())).thenThrow(new RuntimeException("Failed"));
        mockedDepartmentMapper.when(() -> DepartmentMapper.toResponse(any(Department.class))).thenReturn(response);
        assertThrows(ResourceNotFoundException.class, () -> this.departmentService.getDepartmentById(500l));


    }

    @Test
    void getAllDepartment() {

        List<DepartmentResponseDto> responseList = Arrays.asList(response, response);

        when(this.departmentRepository.getAllDepartment()).thenReturn(departmentList);

        List<DepartmentResponseDto> resultList = this.departmentService.getAllDepartment();

        assertEquals(2, responseList.size());
        verify(this.departmentRepository).getAllDepartment();

    }

    @Test
    void getAllDepartmentFailureTest() {

        when(this.departmentRepository.getAllDepartment()).thenThrow(new RuntimeException("Failed"));
        mockedDepartmentMapper.when(() -> DepartmentMapper.toResponse(any(Department.class))).thenReturn(response);
        assertThrows(DatabaseOperationException.class, () -> this.departmentService.getAllDepartment());

    }


    @Test
    void deleteDepartment() {
        when(this.departmentRepository.existById(anyLong())).thenReturn(true);
        doNothing().when(this.departmentRepository).deleteDepartment(anyLong());
        assertDoesNotThrow(() -> this.departmentService.deleteDepartment(1l));

        verify(this.departmentRepository).deleteDepartment(1l);
    }


    @Test
    void deleteDepartmentFailureNotFoundExceptionTest() {

        when(this.departmentRepository.existById(50l)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> this.departmentService.deleteDepartment(50l));

    }

    @Test
    void deleteDepartmentFailureDbTest() {

        when(this.departmentRepository.existById(50l)).thenReturn(true);

        doThrow(new RuntimeException("db Failed")).when(this.departmentRepository).deleteDepartment(anyLong());

        assertThrows(DatabaseOperationException.class, () -> this.departmentService.deleteDepartment(50l));

    }

    @Test
    void updateDepartment() {


        mockedDepartmentMapper.when(() -> DepartmentMapper.toEntity(any(DepartmentRequestDto.class))).thenReturn(department);

        mockedDepartmentMapper.when(() -> DepartmentMapper.toResponse(any(Department.class))).thenReturn(response);

        when(this.departmentRepository.existById(anyLong())).thenReturn(true);
        when(this.departmentRepository.updateDepartment(any(Department.class), anyLong())).thenReturn(department);

        DepartmentResponseDto result = this.departmentService.updateDepartment(request, 100l);
        assertEquals(response.getDepartmentId(), result.getDepartmentId());
        assertNotNull(result);

    }


    @Test
    void UpdateDepartmentFailureNotFoundExceptionTest() {

        when(this.departmentRepository.existById(anyLong())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> this.departmentService.updateDepartment(request, 50l));

    }

    @Test
    void updateDepartmentDatabaseFailedTest() {

        when(this.departmentRepository.existById(anyLong())).thenReturn(true);
        mockedDepartmentMapper.when(() -> DepartmentMapper.toEntity(any(DepartmentRequestDto.class))).thenReturn(department);

        when(this.departmentRepository.updateDepartment(any(Department.class), anyLong())).thenThrow(new RuntimeException());

        assertThrows(DatabaseOperationException.class, () -> this.departmentService.updateDepartment(request, 50l));
    }


}