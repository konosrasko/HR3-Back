package com.open3hr.adeies.app.leaveBalance.entity;

import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.leaveBalance.dto.LeaveBalanceDTO;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class leaveBalanceEntityTest {

    @Test
    public void DTOToEntity(){
        LeaveCategory myLeaveCategory = new LeaveCategory(1,"Normal");
        Employee myEmployee = new Employee(1,"Stavros","Stavridis","sstavridis@ots.gr","6928327132","Kyprou 23",new Date(2023,10,07),true,1,null,null,null,null);

        LeaveBalanceDTO myLeaveBalanceDTO = LeaveBalanceDTO
                .builder()
                .id(1)
                .days(10)
                .daysTaken(5)
                .categoryTitle(myLeaveCategory.getTitle())
                .build();

        LeaveBalance myLeaveBalance = LeaveBalance
                .builder()
                .id(1)
                .days(10)
                .daysTaken(5)
                .employee(myEmployee)
                .category(myLeaveCategory)
                .build();

        LeaveBalance myLeaveBalanceDTOToEntity = new LeaveBalance(myLeaveBalanceDTO,myEmployee,myLeaveCategory);

        assertThat(myLeaveBalance.equals(myLeaveBalanceDTOToEntity)).isTrue();
    }
}
