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
    private Integer id;
    private Integer days;
    private Integer daysTaken;
    private Integer employeeId;
    private Integer leaveCategoryId;

    public LeaveBalanceDTO(LeaveBalance leaveBalance){
        this.id = leaveBalance.getId();
        this.days = leaveBalance.getDays();
        this.daysTaken = leaveBalance.getDaysTaken();
        this.employeeId = leaveBalance.getEmployeeId();
        this.leaveCategoryId = leaveBalance.getLeaveCategoryId();
    }
}
