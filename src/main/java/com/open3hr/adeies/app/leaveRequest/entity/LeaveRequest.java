package com.open3hr.adeies.app.leaveRequest.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "leave_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_category_id", referencedColumnName = "id")
    private LeaveCategory category;

    @Column(name = "submit_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date submitDate;

    @Column(name = "start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date startDate;

    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date endDate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public LeaveRequest(LeaveRequestDTO leaveRequestDTO){
        this.id= leaveRequestDTO.getId();
        this.startDate = leaveRequestDTO.getStartDate();
        this.endDate = leaveRequestDTO.getEndDate();
        this.submitDate = leaveRequestDTO.getSubmitDate();
        this.duration = leaveRequestDTO.getDuration();
        this.status = leaveRequestDTO.getStatus();
    }
    public LeaveRequest(LeaveRequestDTO leaveRequestDTO, Employee employee, LeaveCategory category){
        this.id = leaveRequestDTO.getId();
        this.employee = employee;
        this.startDate = leaveRequestDTO.getStartDate();
        this.endDate = leaveRequestDTO.getEndDate();
        this.submitDate = leaveRequestDTO.getSubmitDate();
        this.duration = leaveRequestDTO.getDuration();
        this.status = leaveRequestDTO.getStatus();
        this.category = category;
    }
}