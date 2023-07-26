package com.open3hr.adeies.entities;

import com.open3hr.adeies.dto.EmployeeDTO;
import com.open3hr.adeies.dto.LeaveBalanceDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name= "last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name = "is_enabled")
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    private Employee employee;

    @OneToMany(mappedBy = "employee",
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.ALL
            })
    private List<LeaveBalance> leaveBalanceList = new ArrayList<>();

    public Employee(EmployeeDTO employeeDTO){
        this.id = employeeDTO.getEmployeeId();
        this.firstName = employeeDTO.getFirstName();
        this.lastName = employeeDTO.getLastName();
        this.email = employeeDTO.getEmail();
        this.mobileNumber = employeeDTO.getMobileNumber();
        this.address = employeeDTO.getAddress();
        this.hireDate = employeeDTO.getHireDate();
        this.enabled = employeeDTO.isEnabled();
        this.leaveBalanceList = employeeDTO.getLeaveBalanceDTOS()
                .stream()
                .map(leaveBalanceDTO -> new LeaveBalance(leaveBalanceDTO, this))
                .toList();
    }
}
