package com.open3hr.adeies.app.leaveCategory.entity;

import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class leaveCategoryEntityTest {

    @Test
    public void DTOToEntity(){
        LeaveCategoryDTO myLeaveCategoryDTO = LeaveCategoryDTO
                .builder()
                .id(1)
                .title("Normal")
                .isActive(true)
                .build();

        LeaveCategory myLeaveCategory = LeaveCategory
                .builder()
                .id(1)
                .title("Normal")
                .isActive(true)
                .build();

        LeaveCategory myLeaveCategoryDTOToEntity = new LeaveCategory(myLeaveCategoryDTO);

        assertThat(myLeaveCategory.equals(myLeaveCategoryDTOToEntity)).isTrue();
    }
}
