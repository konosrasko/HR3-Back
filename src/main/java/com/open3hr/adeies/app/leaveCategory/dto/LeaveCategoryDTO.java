package com.open3hr.adeies.app.leaveCategory.dto;

import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaveCategoryDTO {
    private Integer id;
    private String title;
    private boolean isActive;

    public LeaveCategoryDTO(LeaveCategory leaveCategory){
        this.id = leaveCategory.getId();
        this.title = leaveCategory.getTitle();
        this.isActive = leaveCategory.isActive();
    }
}
