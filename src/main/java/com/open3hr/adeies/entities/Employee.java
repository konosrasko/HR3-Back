package com.open3hr.adeies.entities;


import com.open3hr.adeies.dto.EmployeeDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name= "last_name")
    private String last_name;

    @Column(name="email")
    private String email;

    @Column(name = "mobile_number")
    private String mobile_number;

    @Column(name = "address")
    private String address;

    @Column(name = "hire_date")
    private Date hire_date;

    @Column(name = "is_enable")
    private int is_enable;

    @Column(name = "supervisor_id")
    private int supervisor_id;

    public Employee(EmployeeDTO employeeDTO){
        this.id = employeeDTO.getEmployee_id();
        this.first_name = employeeDTO.getFirst_name();
        this.last_name = employeeDTO.getLast_name();
        this.email = employeeDTO.getEmail();
        this.mobile_number = employeeDTO.getMobile_number();
        this.address = employeeDTO.getAddress();
        this.hire_date = employeeDTO.getHire_date();
        this.is_enable = employeeDTO.getIs_enable();
        this.supervisor_id = employeeDTO.getSupervisor_id();
    }

}
