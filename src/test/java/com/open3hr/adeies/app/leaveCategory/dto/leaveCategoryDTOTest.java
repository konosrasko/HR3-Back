package com.open3hr.adeies.app.leaveCategory.dto;

import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class leaveCategoryDTOTest {

    @Test
    public void EntityToDTO(){
        LeaveCategory myLeaveCategory = LeaveCategory
                .builder()
                .id(1)
                .title("Normal")
                .isActive(true)
                .build();

        LeaveCategoryDTO myLeaveCategoryDTO = LeaveCategoryDTO
                .builder()
                .id(1)
                .title("Normal")
                .isActive(true)
                .build();

        LeaveCategoryDTO myLeaveCategoryEntityToDTO = new LeaveCategoryDTO(myLeaveCategory);

        assertThat(myLeaveCategoryDTO.equals(myLeaveCategoryEntityToDTO)).isTrue();
    }
}
