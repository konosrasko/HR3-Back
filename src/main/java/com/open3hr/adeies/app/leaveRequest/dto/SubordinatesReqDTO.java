package com.open3hr.adeies.app.leaveRequest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.enums.Status;
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
public class SubordinatesReqDTO {
    private Integer leaveId;
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String leaveTitle;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date submitDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endDate;
    private Integer duration;
    private Status status;

    public SubordinatesReqDTO(LeaveRequest leaveRequest, Employee employee){
        this.leaveId = leaveRequest.getId();
        this.employeeId = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.leaveTitle =  leaveRequest.getCategory().getTitle();
        this.submitDate = leaveRequest.getSubmitDate();
        this.startDate = leaveRequest.getStartDate();
        this.endDate = leaveRequest.getEndDate();
        this.duration = leaveRequest.getDuration();
        this.status = leaveRequest.getStatus();
    }
}
