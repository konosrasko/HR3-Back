package com.open3hr.adeies.app.employee.dto;

import com.open3hr.adeies.app.employee.entity.Employee;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class employeeDTOTest {

    @Test
    public void EntityToDTO(){
        Employee myEmployee = Employee
                .builder()
                .id(1)
                .firstName("Michael")
                .lastName("Fotiadis")
                .email("mfotiadis@ots.gr")
                .mobileNumber("6983213213")
                .address("Sikelianou")
                .hireDate(new Date(2023,10,07))
                .enabled(true)
                .supervisorId(1)
                .build();

        EmployeeDTO myEmployeeDTO = EmployeeDTO
                .builder()
                .employeeId(1)
                .firstName("Michael")
                .lastName("Fotiadis")
                .email("mfotiadis@ots.gr")
                .mobileNumber("6983213213")
                .address("Sikelianou")
                .hireDate(new Date(2023,10,07))
                .enabled(true)
                .supervisorId(1)
                .build();

        EmployeeDTO myEmployeeDTOFromEntity = new EmployeeDTO(myEmployee);

        assertThat(myEmployeeDTO.equals(myEmployeeDTOFromEntity)).isTrue();
    }
}
