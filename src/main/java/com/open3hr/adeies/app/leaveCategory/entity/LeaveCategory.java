package com.open3hr.adeies.app.leaveCategory.entity;

import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="leave_category")

public class LeaveCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="title")
    private String title;

    @OneToMany(mappedBy = "category",
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.ALL
            })
    private List<LeaveBalance> balances = new ArrayList<>();

    @OneToMany(mappedBy = "category",
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.ALL
            })
    private List<LeaveRequest> requests = new ArrayList<>();

    @Column(name="is_active")
    private boolean isActive;

    public LeaveCategory(LeaveCategoryDTO leaveCategoryDTO){
        this.id = leaveCategoryDTO.getId();
        this.title = leaveCategoryDTO.getTitle();
    }
}
