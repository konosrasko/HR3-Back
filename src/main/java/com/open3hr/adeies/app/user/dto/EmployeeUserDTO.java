package com.open3hr.adeies.app.user.dto;

import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.enums.Role;
import com.open3hr.adeies.app.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeUserDTO {
    private Integer userId;
    private String username;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private Integer employeeId;
    private boolean isEnabled;
    private boolean isSupervisor;

    public EmployeeUserDTO (Employee employee, User user){
        this.userId = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.employeeId = employee.getId();
        this.isEnabled = user.getIsEnabled();
        this.isSupervisor = user.isSupervisor();
    }
}