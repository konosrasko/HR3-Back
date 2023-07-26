package com.open3hr.adeies.dto;

import com.open3hr.adeies.entities.LeaveCategory;
import com.open3hr.adeies.entities.LeaveRequest;
import com.open3hr.adeies.entities.Role;
import com.open3hr.adeies.entities.Status;
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
    private Status status;

    public LeaveRequestDTO(LeaveRequest leaveRequest){
        this.startDate=leaveRequest.getStartDate();
        this.endDate= leaveRequest.getEndDate();
        this.categoryId = leaveRequest.getCategory().getId();
        this.status = leaveRequest.getStatus();
    }
}