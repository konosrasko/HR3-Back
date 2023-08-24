package com.open3hr.adeies.app.employee.dto;

import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeSupervisorDTO {

    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String address;
    private Date hireDate;
    private boolean enabled;
    private String supervisorLastName;
    private String supervisorFirstName;

    public EmployeeSupervisorDTO(Employee employee, String supervisorLastName, String supervisorFirstName ){
        this.employeeId = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.mobileNumber = employee.getMobileNumber();
        this.address = employee.getAddress();
        this.hireDate = employee.getHireDate();
        this.enabled = employee.isEnabled();
        this.supervisorLastName = supervisorLastName;
        this.supervisorFirstName = supervisorFirstName;
    }
}