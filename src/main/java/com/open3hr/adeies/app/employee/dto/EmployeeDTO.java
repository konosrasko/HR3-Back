package com.open3hr.adeies.app.employee.dto;

import com.open3hr.adeies.app.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String address;
    private Date hireDate;
    private boolean enabled;
    private Integer supervisorId;

    public EmployeeDTO(Employee employee) {
        this.employeeId = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.mobileNumber = employee.getMobileNumber();
        this.address = employee.getAddress();
        this.hireDate = employee.getHireDate();
        this.enabled = employee.isEnabled();
        this.supervisorId = employee.getSupervisorId();
    }
}