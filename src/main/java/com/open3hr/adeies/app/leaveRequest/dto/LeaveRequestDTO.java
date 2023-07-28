package com.open3hr.adeies.app.leaveRequest.dto;

import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
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

    //    private int categoryId;
    private String leaveTitle;
    private Date startDate;
    private Date endDate;
    private Status status;

    public LeaveRequestDTO(LeaveRequest leaveRequest, LeaveCategory category){
        this.leaveTitle = category.getTitle();
        this.startDate = leaveRequest.getStartDate();
        this.endDate = leaveRequest.getEndDate();
        this.status = leaveRequest.getStatus();
    }
}
