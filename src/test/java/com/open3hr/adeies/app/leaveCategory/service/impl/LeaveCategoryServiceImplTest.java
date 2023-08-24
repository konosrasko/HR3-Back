package com.open3hr.adeies.app.leaveCategory.service.impl;

import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.employee.service.impl.EmployeeServiceImpl;
import com.open3hr.adeies.app.enums.Role;
import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.leaveRequest.repository.LeaveRequestRepository;
import com.open3hr.adeies.app.leaveRequest.service.impl.LeaveRequestServiceImpl;
import com.open3hr.adeies.app.user.entity.User;
import com.open3hr.adeies.app.user.repository.UserRepository;
import org.junit.experimental.categories.Categories;
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

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaveCategoryServiceImplTest {
    @Mock
    private LeaveCategoryRepository leaveCategoryRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private LeaveRequestRepository leaveRequestRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LeaveCategoryServiceImpl leaveCategoryService;


    private Employee employee;
    private Employee employee1;
    private Employee employee2;

    private User user;
    private User user1;
    private User user2;
    private LeaveRequestDTO leaveRequestDTO;
    private LeaveCategory leaveCategory;
    private LeaveCategory leaveCategory1;

    private LeaveRequest leaveRequest;
    private LeaveCategoryDTO leaveCategoryDTO;

    @BeforeEach
    public void Init() throws ParseException{


        employee = Employee.builder()
                .id(1)
                .firstName("test")
                .lastName("testiou")
                .email("testopoulos@gmail.com")
                .mobileNumber("6985345634")
                .address("testisias")
                .address("23")
                .supervisorId(2)
                .build();

        employee1 = Employee.builder()
                .id(2)
                .firstName("test")
                .lastName("testiou")
                .email("testopoulos@gmail.com")
                .mobileNumber("6985345634")
                .address("testisias")
                .address("23")
                .supervisorId(1)
                .build();
        employee2 = Employee.builder()
                .id(3)
                .firstName("test1")
                .lastName("testiou1")
                .email("testopoulos111@gmail.com")
                .mobileNumber("6985345634")
                .address("testisias11")
                .address("231")
                .supervisorId(1)
                .build();

        List<LeaveBalance> leaveBalances = new ArrayList<>();
        leaveBalances.add(LeaveBalance.builder()
                .id(1)
                .employee(employee)
                .category(new LeaveCategory(1,"Normal"))
                .days(18).daysTaken(0)
                .build());
        employee.setLeaveBalanceList(leaveBalances);
        user = User.builder()
                .id(1)
                .employee(employee)
                .isEnable(true)
                .role(Role.HR)
                .username("test")
                .password("test")
                .isSupervisor(true)
                .build();

        user1 = User.builder()
                .id(2)
                .employee(employee1)
                .isEnable(true)
                .role(Role.HR)
                .username("test1")
                .password("test1")
                .isSupervisor(true)
                .build();
        user2 = User.builder()
                .id(3)
                .employee(employee2)
                .isEnable(true)
                .role(Role.HR)
                .username("test2")
                .password("test2")
                .isSupervisor(false)
                .build();
        leaveRequestDTO= LeaveRequestDTO.builder()
                .id(1)
                .leaveTitle("Normal")
                .submitDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-22"))
                .startDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-24"))
                .duration(2)
                .status(Status.PENDING)
                .endDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-26"))
                .build();


        leaveCategory = LeaveCategory.builder()
                .id(1)
                .title("KANONIKH")
                .isActive(true)
                .build();
        leaveCategory1 = LeaveCategory.builder()
                .id(2)
                .title("gamou")
                .isActive(true)
                .build();


        leaveRequest =  LeaveRequest.builder()
                .id(1)
                .employee(employee)
                .category(leaveCategory)
                .submitDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-22"))
                .startDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-24"))
                .endDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-26"))
                .status(Status.PENDING)
                .build();
    }
    @Test
    void activeLeaveCategories(){
        List<LeaveCategory> categories = new ArrayList<>();
        categories.add(leaveCategory);
        categories.add(leaveCategory1);

        when(leaveCategoryRepository.findAll()).thenReturn(categories);
        assertEquals(leaveCategoryService.activeLeaveCategories().size(),2);
    }

//    @Test
//    void createNewCategory(){
//        LeaveCategoryDTO leaveCategoryDTO1 = LeaveCategoryDTO.builder()
//                .id(3)
//                .isActive(true)
//                .title("erwtisi")
//                .build();
//
//        when(leaveCategoryRepository.findCategoryByTitle(leaveCategoryDTO1.getTitle())).thenReturn(Optional.empty());
//
//        LeaveCategory expectedCategory = new LeaveCategory(leaveCategoryDTO1);
//        when(leaveCategoryService.createNewCategory(leaveCategoryDTO1)).thenReturn();
//
//        LeaveCategoryDTO resultDTO = leaveCategoryService.createNewCategory(leaveCategoryDTO1);
//
//        assertNotNull(resultDTO);
//        assertEquals(expectedCategory.getTitle(), resultDTO.getTitle());
//    }



}