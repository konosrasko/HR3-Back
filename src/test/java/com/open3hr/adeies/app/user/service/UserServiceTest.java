package com.open3hr.adeies.app.user.service;

import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.employee.service.impl.EmployeeServiceImpl;
import com.open3hr.adeies.app.enums.Role;
import com.open3hr.adeies.app.exceptions.ConflictException;
import com.open3hr.adeies.app.exceptions.NotFoundException;
import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.user.dto.UserDTO;
import com.open3hr.adeies.app.user.entity.User;
import com.open3hr.adeies.app.user.repository.UserRepository;
import com.open3hr.adeies.app.user.service.impl.UserServiceImpl;
import com.open3hr.adeies.configuration.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private LeaveRequest leaveRequest;
    private LeaveRequest leaveRequest1;
    private Employee employee;
    private Employee employee2;
    private Employee employee3;
    private Employee supervisor;
    private User userSupervisor;
    private User userEmployee;
    private User userEmployee2;
    private User userEmployee3;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void init() throws ParseException {

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
                .firstName("testios")
                .lastName("test1")
                .email("testing@gmail.com")
                .mobileNumber("6985345634")
                .address("testAddress")
                .address("1")
                .supervisorId(1)
                .build();

        employee3 = Employee.builder()
                .id(7)
                .firstName("test")
                .lastName("test1")
                .email("testing@gmail.com")
                .mobileNumber("6985345634")
                .address("testAddress")
                .address("1")
                .supervisorId(1)
                .build();


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
                .isEnable(false)
                .role(Role.Employee)
                .username("test2")
                .password("test2")
                .isSupervisor(false)
                .build();

    }

    @Test
    void createAccount() {
    //    Throwable exception = ConflictException.class(
//                ()-> {
//                    when(employeeRepository.findById(userEmployee.getId())).thenReturn(Optional.ofNullable(userEmployee.getEmployee()));
//                    List<User> users = new ArrayList<>();
//                    users.add(userEmployee);
//                    users.add(userEmployee2);
//                    when(userRepository.findAll()).thenReturn(users);
//                }
//        );
//        assertEquals("Ο εργαζόμενος με id " + userEmployee.getId() + " έχει ήδη account.", exception.getMessage());

    }

    @Test
    public void testCreateAccount_Success() {
        // Create a sample userDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setEmployeeId(5);
        userDTO.setUsername("testUser");
        userDTO.setPassword("testPassword");

        // Create a sample employee
        Employee employee = new Employee();
        employee.setId(5);

        when(employeeRepository.findById(5)).thenReturn(Optional.of(employee));
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        UserDTO result = userService.createAccount(userDTO);

        assertTrue(result.isPassTemp());
        assertNotNull(result.getPassword());

        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void updateStatus() {
        when(userRepository.findById(employee2.getId())).thenReturn(Optional.ofNullable(userEmployee2));
        userService.updateStatus(employee2.getId());
        assertEquals(true,userEmployee2.isEnable());
    }

    @Test
    void changeSupervisorRights() {
        when(userRepository.findById(employee2.getId())).thenReturn(Optional.ofNullable(userEmployee2));
        userService.changeSupervisorRights(userEmployee2.getId());
        assertEquals(false,userEmployee2.isSupervisor());
    }

    @Test
    void getUserRoles() {
        when(userRepository.findById(employee2.getId())).thenReturn(Optional.ofNullable(userEmployee2));
        assertEquals(Role.Employee,userEmployee2.getRole());
    }

//    @Test
//    void getUserInfo() {
//        when(userRepository.findById(employee2.getId())).thenReturn(Optional.ofNullable(userEmployee2));
//        when(employeeRepository.findById(employee2.getId())).thenReturn(Optional.ofNullable(employee2));
//        assertEquals(,userService.getEmployeeInfo("test2"));
//    }

    @Test
    void getEmployeeInfo() {
    }

    @Test
    void getEmployeeUserAdmin() {
    }

    @Test
    void getEmployeeUserById() {
    }

    @Test
    void editUser() {
    }

    @Test
    void activateDeactivateUser() {
    }

    @Test
    void userLogout() {
    }
}