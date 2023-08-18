package com.open3hr.adeies.app.employee.service.impl;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.employee.dto.EmployeeSupervisorDTO;
import com.open3hr.adeies.app.employee.dto.miniEmployeeDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.enums.Role;
import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveBalance.repository.LeaveBalanceRepository;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.leaveRequest.repository.LeaveRequestRepository;
import com.open3hr.adeies.app.user.entity.User;
import com.open3hr.adeies.app.user.repository.UserRepository;
import com.open3hr.adeies.app.user.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;



    @ExtendWith(MockitoExtension.class)
    class EmployeeServiceTest {
        @Mock
        private EmployeeRepository employeeRepository;

        @Mock
        private LeaveCategoryRepository leaveCategoryRepository;

        @Mock
        private LeaveRequestRepository leaveRequestRepository;
        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private EmployeeServiceImpl employeeService;


        @Mock
        private LeaveBalanceRepository leaveBalanceRepository;
        private Employee employee;
        private Employee employee1;
        private Employee employee2;

        private User user;
        private User user1;
        private User user2;
        private LeaveRequestDTO leaveRequestDTO;

        private LeaveCategory leaveCategory;

        private LeaveRequest leaveRequest;

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
                    .status(null)
                    .endDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-26"))
                    .build();

            leaveCategory = new LeaveCategory(1,"Normal");
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
        void addNewLeaveRequestTest()
        {
            LeaveCategory leaveCategory = new LeaveCategory(1,"Normal");
            when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
            when(leaveCategoryRepository.findCategoryByTitle(leaveRequestDTO.getLeaveTitle())).thenReturn(Optional.of(leaveCategory));

            LeaveRequestDTO answer = employeeService.addLeaveRequest(leaveRequestDTO,employee.getId());
            assertEquals("PENDING",answer.getStatus().toString());
            Assertions.assertNotNull(answer);

        }

        @Test
        void assignToSupervisorTest()
        {
            when(employeeRepository.findById(employee1.getId())).thenReturn(Optional.of(employee));
            when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee1));
            EmployeeDTO answer = employeeService.assignToSupervisor(employee.getId(),employee1.getId());
            Assertions.assertEquals(employee1.getId(),answer.getSupervisorId());

        }

        @Test
        void unAssignTheSupervisorTest()
        {
            when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee1));
            EmployeeDTO answer = employeeService.unassignedToSupervisor(employee.getId(),employee1.getId());
            Assertions.assertNull(answer.getSupervisorId());
        }

        @Test
        void approveLeaveRequestTest()
        {
            when(leaveRequestRepository.findById(leaveRequest.getId())).thenReturn(Optional.of(leaveRequest));
            LeaveRequestDTO answer = employeeService.approveLeaveRequest(leaveRequest.getId());
            Assertions.assertNotNull(answer);
            Assertions.assertEquals(Status.APPROVED,answer.getStatus());
        }

        @Test
        void findAllDirectSubordinatesTest()
        {
            List<Employee> employeeList = new ArrayList<>();
            employeeList.add(employee1);
            employeeList.add(employee2);

            when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
            when(employeeRepository.findAllSubordinatesOf(employee.getId())).thenReturn(employeeList);
            List<EmployeeSupervisorDTO> employeeSupervisorDTOList = employeeService.findAllDirectSubordinates(employee.getId());
            Assertions.assertEquals(2,employeeSupervisorDTOList.size());
        }


        @Test
        void findEmployeeByUserName()
        {
            when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
            when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
            Employee employee1 = new Employee(employeeService.findEmployeeByUserName(user.getUsername()));
            assertEquals(employee1.getId(),employee.getId());
        }

        @Test
        void declineLeaveRequest() throws ParseException {


            LeaveCategory leaveCategory = new LeaveCategory(1,"Normal");
            LeaveRequest leaveRequest = LeaveRequest.builder()
                    .status(Status.PENDING)
                    .id(1)
                    .employee(employee)
                    .startDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-24"))
                    .endDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-26"))
                    .submitDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-22"))
                    .category(leaveCategory)
                    .duration(2)
                    .build();
            when(leaveRequestRepository.findById(leaveRequest.getId())).thenReturn(Optional.of(leaveRequest));
            LeaveRequestDTO leaveRequestDTO1 = employeeService.declineLeaveRequest(leaveRequest.getId());
            assertEquals(Status.DENIED,leaveRequestDTO1.getStatus());
        }

        @Test
        void findAllSubordinates()
        {
            List<Employee> subordinates = new ArrayList<>();
            subordinates.add(employee1);
            subordinates.add(employee2);

            when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
            when(employeeRepository.findById(employee1.getId())).thenReturn(Optional.of(employee1));
            when(employeeRepository.findById(employee2.getId())).thenReturn(Optional.of(employee2));

            when(employeeRepository.findAllSubordinatesOf(employee.getId())).thenReturn(subordinates);

            List<EmployeeSupervisorDTO> employeeSupervisorDTOS = employeeService.findAllSubordinates(employee.getId());
            assertEquals(employeeSupervisorDTOS.size(),2);
        }

        @Test
        void findAllSupervisorsTest()
        {
            List<Employee> supervisorsList = new ArrayList<>();
            supervisorsList.add(employee);
            when(employeeRepository.findAllSupervisors()).thenReturn(supervisorsList);
            List<miniEmployeeDTO> miniEmployeeDTOList = employeeService.findAllSupervisors();
            Assertions.assertEquals(1,miniEmployeeDTOList.size());
        }
    }
