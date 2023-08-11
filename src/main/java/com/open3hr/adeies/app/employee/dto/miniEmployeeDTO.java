package com.open3hr.adeies.app.employee.dto;

import com.open3hr.adeies.app.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class miniEmployeeDTO {
    private Integer employeeId;
    private String lastName;


    public miniEmployeeDTO(Employee employee){
        this.employeeId = employee.getId();
        this.lastName = employee.getLastName();
    }
}
