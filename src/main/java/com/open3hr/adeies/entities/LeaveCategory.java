package com.open3hr.adeies.entities;

import com.open3hr.adeies.dto.LeaveCategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name="isActive")
    private boolean isActive;

    public LeaveCategory(LeaveCategoryDTO leaveCategoryDTO){
        this.id = leaveCategoryDTO.getId();
        this.title = leaveCategoryDTO.getTitle();
    }
}
