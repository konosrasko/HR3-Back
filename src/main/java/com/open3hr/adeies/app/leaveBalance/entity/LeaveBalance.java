package com.open3hr.adeies.app.leaveBalance.entity;

import com.open3hr.adeies.app.leaveBalance.dto.LeaveBalanceDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    private Integer id;

    @Column(name = "days")
    private Integer days;

    @Column(name = "days_taken")
    private Integer daysTaken;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.DETACH,
                    CascadeType.REFRESH,
            })
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.DETACH,
                    CascadeType.REFRESH,
            })
    @JoinColumn(name = "leave_category_id", referencedColumnName = "id")
    private LeaveCategory category;

//    public LeaveBalance(LeaveBalanceDTO leaveBalanceDTO){
//        this.id = leaveBalanceDTO.getId();
//        this.days = leaveBalanceDTO.getDays();
//        this.daysTaken = leaveBalanceDTO.getDaysTaken();
//    }

    public LeaveBalance(LeaveBalanceDTO leaveBalanceDTO, Employee employee, LeaveCategory category){
        this.id = leaveBalanceDTO.getId();
        this.days = leaveBalanceDTO.getDays();
        this.daysTaken = leaveBalanceDTO.getDaysTaken();
        this.employee = employee;
        this.category = category;
    }

    public void addDaysTaken(int daysToAdd){
        this.daysTaken += daysToAdd;
    }
}
