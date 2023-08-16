package com.open3hr.adeies.app.employee.service.impl;

import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveBalance.repository.LeaveBalanceRepository;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.repository.LeaveRequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;



    @ExtendWith(MockitoExtension.class)
    class EmployeeServiceTest {
        @Mock
        private EmployeeRepository employeeRepository;

        @Mock
        private LeaveCategoryRepository leaveCategoryRepository;

        @Mock
        private LeaveRequestRepository leaveRequestRepository;

        @InjectMocks
        private EmployeeServiceImpl employeeService;

        @Mock
        private LeaveBalanceRepository leaveBalanceRepository;
        private Employee employee;
        private LeaveRequestDTO leaveRequestDTO;

        @BeforeEach
        public void init() throws ParseException {


            employee = Employee.builder()
                    .id(1)
                    .firstName("test")
                    .lastName("testiou")
                    .email("testopoulos@gmail.com")
                    .mobileNumber("6985345634")
                    .address("testisias")
                    .address("23")
                    .build();
            List<LeaveBalance> leaveBalances = new ArrayList<>();
            leaveBalances.add(LeaveBalance.builder()
                    .id(1)
                    .employee(employee)
                    .category(new LeaveCategory(1,"Normal"))
                    .days(18).daysTaken(0)
                    .build());
            employee.setLeaveBalanceList(leaveBalances);

            leaveRequestDTO= LeaveRequestDTO.builder()
                    .id(1)
                    .leaveTitle("Normal")
                    .submitDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-22"))
                    .startDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-24"))
                    .duration(2)
                    .status(null)
                    .endDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-26"))
                    .build();
        }

        @Test
        void addNewLeaveRequest()
        {

            when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
            LeaveCategory leaveCategory = new LeaveCategory(1,"Normal");
            when(leaveCategoryRepository.findCategoryByTitle(leaveRequestDTO.getLeaveTitle())).thenReturn(Optional.of(leaveCategory));
            LeaveRequestDTO answer = employeeService.addLeaveRequest(leaveRequestDTO,employee.getId());


            Assertions.assertEquals("PENDING",answer.getStatus().toString());
            Assertions.assertNotNull(answer);

        }
    }

