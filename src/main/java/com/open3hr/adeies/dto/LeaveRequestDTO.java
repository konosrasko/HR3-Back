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
    private Date start_date;
    private Date end_date;

    public LeaveRequestDTO(LeaveRequest leaveRequest){
        this.start_date=leaveRequest.getStart_date();
        this.end_date= leaveRequest.getEnd_date();
    }
}
