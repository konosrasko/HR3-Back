package com.open3hr.adeies.entities;

import com.open3hr.adeies.dto.LeaveBalanceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leave_balance")
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "days")
    private Long days;

    @Column(name = "days_taken")
    private Long days_taken;

    @Column(name = "employee_id")
    private Long employee_id;

    @Column(name = "leave_category_id")
    private Long leave_category_id;

    public LeaveBalance(LeaveBalanceDTO leaveBalanceDTO){
        this.id = leaveBalanceDTO.getId();
        this.days = leaveBalanceDTO.getDays_taken();
        this.days_taken = leaveBalanceDTO.getDays_taken();
        this.employee_id = leaveBalanceDTO.getEmployee_id();
        this.leave_category_id = leaveBalanceDTO.getLeave_category_id();
    }
}
