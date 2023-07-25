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
    private int employee_id;

    @Column(name = "leave_category_id")
    @JoinColumn(name = "leave_category_id")
    private int leave_category_id;

    @Column(name = "submit_date")
    private Date submit_date;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "end_date")
    private Date end_date;

    @Column(name = "duration")
    private int duration;

    @Column(name = "status")
    private String status;

    public LeaveRequest(LeaveRequestDTO leaveRequestDTO){
        // leave category id
        this.start_date = leaveRequestDTO.getStart_date();
        this.end_date = leaveRequestDTO.getEnd_date();
        this.submit_date = new Date();
    }

}
