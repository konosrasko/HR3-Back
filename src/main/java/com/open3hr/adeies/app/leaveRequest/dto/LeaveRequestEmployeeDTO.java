package com.open3hr.adeies.app.leaveRequest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.open3hr.adeies.app.employee.entity.Employee;
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
public class LeaveRequestEmployeeDTO {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private Integer leaveRequestId;
    private String leaveTitle;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Integer duration;
    private Status status;

    public LeaveRequestEmployeeDTO(LeaveRequest leaveRequest, LeaveCategory leaveCategory, Employee employee){
        this.employeeId = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.leaveRequestId = leaveRequest.getId();
        this.leaveTitle = leaveCategory.getTitle();
        this.startDate = leaveRequest.getStartDate();
        this.endDate = leaveRequest.getEndDate();
        this.duration = leaveRequest.getDuration();
        this.status = leaveRequest.getStatus();
    }
}
