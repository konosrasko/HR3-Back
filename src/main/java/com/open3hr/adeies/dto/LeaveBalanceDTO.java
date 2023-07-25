package com.open3hr.adeies.dto;

import com.open3hr.adeies.entities.LeaveBalance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaveBalanceDTO {
    private Long id;
    private Long days;
    private Long days_taken;
    private Long employee_id;
    private Long leave_category_id;

    public LeaveBalanceDTO(LeaveBalance leaveBalance){
        this.id = leaveBalance.getId();
        this.days = leaveBalance.getDays();
        this.days_taken = leaveBalance.getDays_taken();
        this.employee_id = leaveBalance.getEmployee_id();
        this.leave_category_id = leaveBalance.getLeaveCategoryId();
    }
}
