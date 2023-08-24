package com.open3hr.adeies.app.employee.entity;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class employeeEntityTest {

    @Test
    public void DTOToEntity(){
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

        Employee myEmployeeFromDTO = new Employee(myEmployeeDTO);

        Employee employee = Employee
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

        assertThat(employee.equals(myEmployeeFromDTO)).isTrue();
    }
}
