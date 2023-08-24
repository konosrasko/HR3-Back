package com.open3hr.adeies.app.leaveCategory.entity;

import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
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
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.ALL
            })
    private List<LeaveRequest> requests = new ArrayList<>();

    @Column(name="is_active")
    private boolean isActive;

    public LeaveCategory(LeaveCategoryDTO leaveCategoryDTO){
        this.id = leaveCategoryDTO.getId();
        this.title = leaveCategoryDTO.getTitle();
        this.isActive = leaveCategoryDTO.isActive();
    }

    public LeaveCategory(int id,String title)
    {
        this.id = id;
        this.title = title;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        LeaveCategory other = (LeaveCategory) obj;
        return id != null && id.equals(other.id);
    }
}