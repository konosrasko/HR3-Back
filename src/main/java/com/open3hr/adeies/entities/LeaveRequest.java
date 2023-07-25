package com.open3hr.adeies.entities;


import com.open3hr.adeies.dto.LeaveBalanceDTO;
import com.open3hr.adeies.dto.LeaveRequestDTO;
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
    private int id;

    @Column(name = "employee_id")
    @JoinColumn(name = "employee_id")
    private int employeeId;

    @Column(name = "leave_category_id")
    @JoinColumn(name = "leave_category_id")
    private int leaveCategoryId;

    @Column(name = "submit_date")
    private Date submitDate;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "duration")
    private int duration;

    @Column(name = "status")
    private String status;

    public LeaveRequest(LeaveRequestDTO leaveRequestDTO){
        // leave category id
        this.startDate = leaveRequestDTO.getStartDate();
        this.endDate = leaveRequestDTO.getEndDate();
        this.submitDate = new Date();
    }

}
