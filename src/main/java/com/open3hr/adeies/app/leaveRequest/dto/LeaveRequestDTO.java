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

    private Integer id;
    private String leaveTitle;
    private Date submitDate;
    private Date startDate;
    private Date endDate;
    private Integer duration;
    private Status status;

    public LeaveRequestDTO(LeaveRequest leaveRequest, LeaveCategory category){
        this.id = leaveRequest.getId();
        this.leaveTitle = category.getTitle();
        this.submitDate = leaveRequest.getSubmitDate();
        this.startDate = leaveRequest.getStartDate();
        this.endDate = leaveRequest.getEndDate();
        this.duration = leaveRequest.getDuration();
        this.status = leaveRequest.getStatus();
    }
}