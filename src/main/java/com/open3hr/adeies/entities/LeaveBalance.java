package com.open3hr.adeies.entities;

import com.open3hr.adeies.dto.LeaveBalanceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leave_balance")
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "days")
    private Long days;

    @Column(name = "days_taken")
    private Long daysTaken;

    @OneToMany(mappedBy = "",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.DETACH,
                    CascadeType.REFRESH,
            })
    @JoinColumn(name = "leave_category_id", referencedColumnName = "id")
    private Long leaveCategoryId;

    public LeaveBalance(LeaveBalanceDTO leaveBalanceDTO){
        this.id = leaveBalanceDTO.getId();
        this.days = leaveBalanceDTO.getDays();
        this.daysTaken = leaveBalanceDTO.getDaysTaken();
        this.employeeId = leaveBalanceDTO.getEmployeeId();
        this.leaveCategoryId = leaveBalanceDTO.getLeaveCategoryId();
    }
}
