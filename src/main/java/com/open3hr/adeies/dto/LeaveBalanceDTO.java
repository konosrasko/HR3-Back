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
    private Long daysTaken;
    private Long employeeId;
    private Long leaveCategoryId;

    public LeaveBalanceDTO(LeaveBalance leaveBalance){
        this.id = leaveBalance.getId();
        this.days = leaveBalance.getDays();
        this.daysTaken = leaveBalance.getDaysTaken();
        this.leaveCategoryId = leaveBalance.getLeaveCategoryId();
    }
}
