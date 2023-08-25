package com.open3hr.adeies.app.leaveRequest.service.impl;


import com.open3hr.adeies.app.employee.dto.EmployeeSupervisorDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.enums.Role;
import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveBalance.repository.LeaveBalanceRepository;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.dto.SubordinatesReqDTO;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.leaveRequest.repository.LeaveRequestRepository;
import com.open3hr.adeies.app.leaveRequest.service.LeaveRequestService;
import com.open3hr.adeies.app.user.entity.User;
import com.open3hr.adeies.app.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LeaveRequestServiceTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private LeaveCategoryRepository leaveCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;
    @InjectMocks
    private LeaveRequestServiceImpl leaveRequestService;

    private LeaveRequest leaveRequest;
    private LeaveRequest leaveRequest1;
    private Employee employee;
    private Employee employee2;
    private Employee supervisor;
    private User userSupervisor;
    private User userEmployee;
    private User userEmployee2;
    private LeaveCategory leaveCategory;

    @BeforeEach
    public void init() throws ParseException{

        supervisor = Employee.builder()
                .id(1)
                .firstName("super")
                .lastName("visor")
                .email("supervisor@gmail.com")
                .mobileNumber("6985345634")
                .address("testAddress")
                .address("2")
                .supervisorId(0)
                .build();

        employee = Employee.builder()
                .id(2)
                .firstName("test")
                .lastName("test1")
                .email("testing@gmail.com")
                .mobileNumber("6985345634")
                .address("testAddress")
                .address("1")
                .supervisorId(1)
                .build();

        employee2 = Employee.builder()
                .id(3)
                .firstName("test")
                .lastName("test1")
                .email("testing@gmail.com")
                .mobileNumber("6985345634")
                .address("testAddress")
                .address("1")
                .supervisorId(1)
                .build();

        leaveCategory = new LeaveCategory(1,"Normal");

        List<LeaveBalance> leaveBalances = new ArrayList<>();
        leaveBalances.add(LeaveBalance.builder()
                .id(2)
                .employee(employee)
                .category(leaveCategory)
                .days(18).daysTaken(0)
                .build());
        employee.setLeaveBalanceList(leaveBalances);
        leaveBalances.add(LeaveBalance.builder()
                .id(2)
                .employee(employee2)
                .category(leaveCategory)
                .days(18).daysTaken(0)
                .build());
        employee2.setLeaveBalanceList(leaveBalances);

        userSupervisor = User.builder()
                .id(1)
                .employee(supervisor)
                .isEnable(true)
                .role(Role.HR)
                .username("test")
                .password("test")
                .isSupervisor(true)
                .build();

        userEmployee = User.builder()
                .id(2)
                .employee(employee)
                .isEnable(true)
                .role(Role.Employee)
                .username("test1")
                .password("test1")
                .isSupervisor(false)
                .build();

        userEmployee2 = User.builder()
                .id(3)
                .employee(employee2)
                .isEnable(true)
                .role(Role.Employee)
                .username("test1")
                .password("test1")
                .isSupervisor(false)
                .build();

        leaveRequest =  LeaveRequest.builder()
                .id(1)
                .employee(employee)
                .category(leaveCategory)
                .submitDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-09-22"))
                .startDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-09-24"))
                .endDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-09-26"))
                .duration(3)
                .status(Status.PENDING)
                .build();


        leaveRequest1 =  LeaveRequest.builder()
                .id(2)
                .employee(employee2)
                .category(leaveCategory)
                .submitDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-09-22"))
                .startDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-09-24"))
                .endDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-09-26"))
                .status(Status.PENDING)
                .build();

    }

    @Test
    void findRequestsForAnEmployee(){
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        leaveRequests.add(leaveRequest);
        when(leaveRequestRepository.findAll()).thenReturn(leaveRequests);
        when(leaveCategoryRepository.findCategoryByTitle(leaveRequest.getCategory().getTitle())).thenReturn(Optional.of(leaveRequest.getCategory()));
        List<LeaveRequestDTO> leaveRequestDTOlist = leaveRequestService.findRequestsForAnEmployee(employee.getId());
        assertEquals(1,leaveRequestDTOlist.size());
    }

    @Test
    void getPendingRequests(){
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        leaveRequests.add(leaveRequest);
        when(leaveRequestRepository.findAll()).thenReturn(leaveRequests);
        when(leaveCategoryRepository.findById(leaveRequest.getCategory().getId())).thenReturn(Optional.of(leaveRequest.getCategory()));
        List<LeaveRequestDTO> pendingLeaveRequestDTOlist = leaveRequestService.getPendingRequests();
        assertEquals(1,pendingLeaveRequestDTOlist.size());
    }

    @Test
    void getSubordinatesReq(){
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        leaveRequests.add(leaveRequest);
        when(leaveRequestRepository.findLeaveRequestsForSupervisedEmployees(supervisor.getId())).thenReturn(leaveRequests);
        List<SubordinatesReqDTO> subordinatesReq= leaveRequestService.getSubordinatesReq(supervisor.getId());
        assertEquals(1,subordinatesReq.size());
        assertEquals(employee.getFirstName(),subordinatesReq.get(0).getFirstName());
    }

    @Test
    void getAllSubordinatesReq(){
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        leaveRequests.add(leaveRequest);
        leaveRequests.add(leaveRequest1);
        List<EmployeeSupervisorDTO> subordinates = new ArrayList<>();
        EmployeeSupervisorDTO employeeSupervisorDTO = new EmployeeSupervisorDTO(employee,supervisor.getLastName(), supervisor.getFirstName());
        EmployeeSupervisorDTO employeeSupervisorDTO2 = new EmployeeSupervisorDTO(employee2,supervisor.getLastName(), supervisor.getFirstName());
        subordinates.add(employeeSupervisorDTO);
        subordinates.add(employeeSupervisorDTO2);
        when(leaveRequestRepository.leaveRequestHistoryOfEmployee(employeeSupervisorDTO.getEmployeeId())).thenReturn(leaveRequests);
        List<SubordinatesReqDTO> subReqList = leaveRequestService.getAllSubordinatesReq(supervisor.getSupervisorId(),subordinates);
        assertEquals(2,subReqList.size());
    }

    @Test
    void editLeaveRequestTest() throws ParseException {
        LeaveRequestDTO editedLeaveRequest = LeaveRequestDTO.builder()
                .id(leaveRequest.getId())
                .leaveTitle(leaveRequest.getCategory().getTitle())
                .status(leaveRequest.getStatus())
                .submitDate(leaveRequest.getSubmitDate())
                .startDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-09-26"))
                .endDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-10-29"))
                .duration(3)
                .build();
        when(leaveRequestRepository.findById(editedLeaveRequest.getId())).thenReturn(Optional.of(leaveRequest));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(leaveCategoryRepository.findCategoryByTitle(editedLeaveRequest.getLeaveTitle())).thenReturn(Optional.of(leaveCategory));

        LeaveRequestDTO answer = leaveRequestService.editLeaveRequest(editedLeaveRequest,employee.getId());
        Assert.assertEquals(editedLeaveRequest,answer);

    }

    @Test
    void declineLeaveRequest()
    {
        
    }
}
