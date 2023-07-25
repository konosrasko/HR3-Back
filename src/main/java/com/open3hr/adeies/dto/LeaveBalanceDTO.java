package com.open3hr.adeies.dto;

import com.open3hr.adeies.entities.LeaveBalance;
import com.open3hr.adeies.entities.LeaveCategory;
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
//    private LeaveCategory category;

    public LeaveBalanceDTO(LeaveBalance leaveBalance){
        this.id = leaveBalance.getId();
        this.days = leaveBalance.getDays();
        this.daysTaken = leaveBalance.getDaysTaken();
//        this.category = leaveBalance.getLeaveCategory();
    }
}