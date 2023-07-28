package com.open3hr.adeies.app.leaveRequest.entity;


import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "leave_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade  = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH
    } )
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, cascade  = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "leave_category_id", referencedColumnName = "id")
    private LeaveCategory category;

    @Column(name = "submit_date")
    private Date submitDate;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public LeaveRequest(LeaveRequestDTO leaveRequestDTO, Employee employee, LeaveCategory category){
        this.employee = employee;
        this.startDate = leaveRequestDTO.getStartDate();
        this.endDate = leaveRequestDTO.getEndDate();
        this.submitDate = new Date();
        this.status = leaveRequestDTO.getStatus();
        this.category = category;
    }
}