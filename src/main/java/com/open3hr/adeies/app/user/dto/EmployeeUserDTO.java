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

    private String username;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private boolean isEnabled;


    public EmployeeUserDTO (Employee employee, User user){
        this.username = user.getUsername();
        this.role = user.getRole();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.isEnabled = user.getIsEnabled();
    }

}
