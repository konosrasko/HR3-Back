package com.open3hr.adeies.dto;

import com.open3hr.adeies.entities.LeaveCategory;
import com.open3hr.adeies.entities.LeaveRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LeaveRequestDTO {

    private int categoryId;
    private Date startDate;
    private Date endDate;

    public LeaveRequestDTO(LeaveRequest leaveRequest){
        this.startDate=leaveRequest.getStartDate();
        this.endDate= leaveRequest.getEndDate();
        this.categoryId = leaveRequest.getCategory().getId();
    }

}