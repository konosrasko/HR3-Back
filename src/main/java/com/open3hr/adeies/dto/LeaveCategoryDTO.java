package com.open3hr.adeies.dto;

import com.open3hr.adeies.entities.LeaveCategory;
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

    public LeaveCategoryDTO(LeaveCategory leaveCategory){
        this.id = leaveCategory.getId();
        this.title = leaveCategory.getTitle();
    }
}
