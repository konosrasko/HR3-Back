package com.open3hr.adeies.app.leaveBalance.service.impl;

import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.enums.Role;
import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.leaveBalance.dto.LeaveBalanceDTO;
import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveBalance.repository.LeaveBalanceRepository;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.leaveRequest.repository.LeaveRequestRepository;
import com.open3hr.adeies.app.user.entity.User;
import com.open3hr.adeies.app.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveBalanceServiceImplTest {
    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private LeaveCategoryRepository leaveCategoryRepository;

    @Mock
    private LeaveRequestRepository leaveRequestRepository;
    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private LeaveBalanceServiceImpl leaveBalanceService;

    private Employee employee;
    private Employee employee1;
    private Employee employee2;

    private User user;
    private User user1;
    private User user2;
    private LeaveRequestDTO leaveRequestDTO;

    private LeaveCategory leaveCategory;

    private LeaveRequest leaveRequest;
    private LeaveBalanceDTO leaveBalanceDTO;

    @BeforeEach
    public void Init() throws ParseException {
        leaveCategory = new LeaveCategory(1, "Normal");

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

        leaveRequestDTO = LeaveRequestDTO.builder()
                .id(1)
                .leaveTitle("Normal")
                .submitDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-22"))
                .startDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-24"))
                .duration(2)
                .status(null)
                .endDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-26"))
                .build();

        leaveCategory = new LeaveCategory(1, "Normal");
        LeaveCategory leaveCategory1 = new LeaveCategory(2, "allo");
        leaveRequest = LeaveRequest.builder()
                .id(1)
                .employee(employee)
                .category(leaveCategory)
                .submitDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-22"))
                .startDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-24"))
                .endDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-26"))
                .status(Status.PENDING)
                .build();

        List<LeaveBalance> leaveBalances = new ArrayList<>();

        leaveBalances.add(LeaveBalance.builder()
                .id(1)
                .employee(employee)
                .category(new LeaveCategory(1,"Normal"))
                .days(18).daysTaken(0)
                .build());
        employee.setLeaveBalanceList(leaveBalances);


    }
    @Test
    void addLeaveBalanceToEmployee(){
        LeaveBalanceDTO leaveBalanceDTO1 = new LeaveBalanceDTO();
        leaveBalanceDTO1.builder()
                        .categoryTitle("allo")
                        .id(2)
                        .days(20)
                        .build();

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(leaveCategoryRepository.findCategoryByTitle(leaveBalanceDTO1.getCategoryTitle())).thenReturn(Optional.of(leaveCategory));
        leaveBalanceService.addLeaveBalanceToEmployee(leaveBalanceDTO1,employee.getId());
        assertEquals(1,employee.getLeaveBalanceList().size());
    }
    @Test
    void testDeleteLeaveBalanceOfEmployee() {
        int leaveBalanceIdToDelete = 2;

        LeaveBalance leaveBalanceToDelete = new LeaveBalance();
        leaveBalanceToDelete.setId(leaveBalanceIdToDelete);
        leaveBalanceToDelete.setEmployee(employee);

        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(leaveBalanceRepository.findAll()).thenReturn(List.of(leaveBalanceToDelete));

        leaveBalanceService.deleteLeaveBalanceOfEmployee(employee.getId(), leaveBalanceIdToDelete);

        verify(leaveBalanceRepository, times(1)).deleteById(leaveBalanceIdToDelete);
    }
    @Test
    void testEditLeaveBalanceOfEmployee() {
        LeaveBalanceDTO leaveBalanceDTO = LeaveBalanceDTO.builder()
                .id(1)
                .categoryTitle("Normal")
                .days(20)
                .daysTaken(5)
                .build();

        LeaveCategory leaveCategory = new LeaveCategory();
        leaveCategory.setId(1);
        leaveCategory.setTitle("Normal");

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        when(leaveCategoryRepository.findCategoryByTitle(anyString())).thenReturn(Optional.of(leaveCategory));

        leaveBalanceService.editLeaveBalanceOfEmployee(leaveBalanceDTO, employee.getId());

        verify(leaveBalanceRepository, times(1)).save(any(LeaveBalance.class));
    }
    @Test
    void testShowBalancesOfEmployee() {
        LeaveBalance leaveBalance = LeaveBalance.builder()
                .id(1)
                .employee(employee)
                .category(leaveCategory)
                .days(18)
                .daysTaken(0)
                .build();

        List<LeaveBalance> leaveBalances = new ArrayList<>();
        leaveBalances.add(leaveBalance);

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(leaveCategoryRepository.findById(leaveBalance.getCategory().getId())).thenReturn(Optional.of(leaveCategory));

        List<LeaveBalanceDTO> result = leaveBalanceService.showBalancesOfEmployee(employee.getId());

        assertEquals(1, result.size());
        assertEquals(leaveBalance.getId(), result.get(0).getId());
        assertEquals(leaveCategory.getTitle(), result.get(0).getCategoryTitle());
        assertEquals(leaveBalance.getDays(), result.get(0).getDays());
        assertEquals(leaveBalance.getDaysTaken(), result.get(0).getDaysTaken());
    }

    @Test
    void findLeaveBalanceById(){
        LeaveBalance leaveBalance = LeaveBalance.builder()
                .id(1)
                .employee(employee)
                .category(leaveCategory)
                .days(18)
                .daysTaken(0)
                .build();

        List<LeaveBalance> leaveBalances = new ArrayList<>();
        leaveBalances.add(leaveBalance);
        leaveCategory.setBalances(leaveBalances);

        when(leaveBalanceRepository.findById(1)).thenReturn(Optional.of(leaveBalance));
        when(leaveCategoryRepository.findById(leaveBalance.getId())).thenReturn(Optional.of(leaveCategory));

        assertNotNull(leaveBalanceService.findById(1));
    }

    @Test
    void testFindAll(){
        LeaveBalance leaveBalance = LeaveBalance.builder()
                .id(1)
                .employee(employee)
                .category(leaveCategory)
                .days(18)
                .daysTaken(0)
                .build();

        LeaveCategory leaveCategory = new LeaveCategory();
        leaveCategory.setId(1);

        when(leaveBalanceRepository.findAll()).thenReturn(List.of(leaveBalance));
        when(leaveCategoryRepository.findById(leaveCategory.getId())).thenReturn(Optional.of(leaveCategory));

        List<LeaveBalanceDTO> result = leaveBalanceService.findAll();

        verify(leaveBalanceRepository, times(1)).findAll();
        verify(leaveCategoryRepository, times(1)).findById(leaveCategory.getId());

        assertEquals(1, result.size());
        LeaveBalanceDTO dto = result.get(0);
        assertEquals(leaveBalance.getId(), dto.getId());

    }

}