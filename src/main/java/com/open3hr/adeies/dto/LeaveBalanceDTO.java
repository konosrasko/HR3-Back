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
    private String categoryTitle;
    private Integer days;
    private Integer daysTaken;

    public LeaveBalanceDTO(LeaveBalance leaveBalance, LeaveCategory category){
        this.id = leaveBalance.getId();
        this.categoryTitle = category.getTitle();
        this.days = leaveBalance.getDays();
        this.daysTaken = leaveBalance.getDaysTaken();
    }
}