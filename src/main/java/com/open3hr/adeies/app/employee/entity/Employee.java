package com.open3hr.adeies.app.employee.entity;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @Column(name= "email")
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name = "is_enabled")
    private boolean enabled;

    @Column(name = "supervisor_id")
    private Integer supervisorId;

    @OneToMany(mappedBy = "employee",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.ALL
            })
    private List<LeaveBalance> leaveBalanceList = new ArrayList<>();

    @OneToMany(mappedBy = "employee",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.ALL
            })
    private List<LeaveRequest> requestList = new ArrayList<>();

    @OneToOne(mappedBy = "employee",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            })
    private User user;

    @OneToMany(mappedBy = "employee",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    private List<LeaveRequest> leaveRequest = new ArrayList<>();

    public Employee(EmployeeDTO employeeDTO){
        this.id = employeeDTO.getEmployeeId();
        this.firstName = employeeDTO.getFirstName();
        this.lastName = employeeDTO.getLastName();
        this.email = employeeDTO.getEmail();
        this.mobileNumber = employeeDTO.getMobileNumber();
        this.address = employeeDTO.getAddress();
        this.hireDate = employeeDTO.getHireDate();
        this.enabled = employeeDTO.isEnabled();
        this.supervisorId = employeeDTO.getSupervisorId();
    }

    public LeaveBalance findBalanceOfCategory(LeaveCategory leaveCategory){
        for(LeaveBalance leaveBalance: this.leaveBalanceList){
            if (leaveBalance.getCategory().equals(leaveCategory)){
                return leaveBalance;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                enabled == employee.enabled &&
                supervisorId == employee.supervisorId &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(mobileNumber, employee.mobileNumber) &&
                Objects.equals(address, employee.address) &&
                Objects.equals(hireDate, employee.hireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, mobileNumber, address, hireDate, enabled, supervisorId);
    }
}
