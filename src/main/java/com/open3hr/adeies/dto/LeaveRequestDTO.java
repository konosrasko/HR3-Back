package com.open3hr.adeies.dto;


import com.open3hr.adeies.entities.LeaveCategory;
import com.open3hr.adeies.entities.LeaveRequest;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class LeaveRequestDTO {

    private LeaveCategory leaveCategory;
    private Date startDate;
    private Date endDate;

    public LeaveRequestDTO(LeaveRequest leaveRequest){
        this.startDate=leaveRequest.getStartDate();
        this.endDate= leaveRequest.getEndDate();
    }
}
