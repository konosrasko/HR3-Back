package com.open3hr.adeies.dto;

import com.open3hr.adeies.entities.Employee;
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
    private Integer employee_id;
    private String first_name;
    private String last_name;
    private String email;
    private String mobile_number;
    private String address;
    private Date hire_date;
    private Integer is_enable;
    private Integer supervisor_id;

    public EmployeeDTO(Employee employee) {
        this.employee_id = getEmployee_id();
        this.first_name = getFirst_name();
        this.last_name = getLast_name();
        this.email = getEmail();
        this.mobile_number = getMobile_number();
        this.address = getAddress();
        this.hire_date = getHire_date();
        this.is_enable = getIs_enable();
        this.supervisor_id = getSupervisor_id();

    }

}
