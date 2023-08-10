package com.open3hr.adeies.app.employee.controller;


import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.employee.service.EmployeeService;
import com.open3hr.adeies.app.leaveBalance.dto.LeaveBalanceDTO;
import com.open3hr.adeies.app.leaveBalance.service.LeaveBalanceService;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;
    @Autowired
    private LeaveBalanceService leaveBalanceService;

    //used in: http://localhost:4200/home/leaves/add
    @PostMapping("/leaverequests/add")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public LeaveRequestDTO postNewRequestForMe(@RequestBody LeaveRequestDTO leaveRequestDTO){
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        int id = userService.getUserInfo(loggedUsername).getEmployeeId();
        return employeeService.addLeaveRequest(leaveRequestDTO,id);
    }

    //used in: http://localhost:4200/home/leaves/add (by HR only)
    @PostMapping("/{id}/leaverequests/add")
    @PreAuthorize("hasRole('HR')")
    public LeaveRequestDTO postNewRequestsForAnother(@RequestBody LeaveRequestDTO leaveRequestDTO, @PathVariable Integer id){
        return employeeService.addLeaveRequest(leaveRequestDTO,id);
    }

    //used in: http://localhost:4200/home/leaves/add
    @GetMapping("/balance")
    @PreAuthorize("hasRole('HR') OR hasRole('Admin') OR hasRole('Employee')")
    public List<LeaveBalanceDTO> getMyLeaveBalances(){
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        int id = userService.getUserInfo(loggedUsername).getEmployeeId();
        return leaveBalanceService.showBalancesOfEmployee(id);
    }
    //used in: http://localhost:4200/home/leaves/add (by HR only)
    @GetMapping("/{id}/balance")
    @PreAuthorize("hasRole('HR')")
    public List<LeaveBalanceDTO> getAllLeaveBalancesOfAnother(@PathVariable Integer id){
        return leaveBalanceService.showBalancesOfEmployee(id);
    }





    /* -------- v Undocumented v -------- */
    /* ---------------------------------- */
    @GetMapping("")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR')")
    public List<EmployeeDTO> getAllEmployees(){
        return employeeService.findAllEmployees();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR')")
    public EmployeeDTO getEmployeeById(@PathVariable Integer id){
        return employeeService.findEmployeeById(id);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('HR')")
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeService.addEmployee(employeeDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public void deleteById(@PathVariable Integer id){
        employeeService.deleteById(id);
    }

    @GetMapping("/withoutAccount")
    @PreAuthorize("hasRole('HR') OR hasRole('Admin')")
    public List<EmployeeDTO> employeesWithoutAccount(){
        return employeeService.employeesWithoutAccount();
    }

    @PostMapping("/{id}/leavebalance")
    @PreAuthorize("hasRole('HR')")
    public String addLeaveBalanceToAnEmployee(@PathVariable Integer id, @RequestBody LeaveBalanceDTO leaveBalanceDTO){
        leaveBalanceService.addLeaveBalanceToEmployee(leaveBalanceDTO, id);
        return ("New balance added to employee with id " + id);
    }

    @PutMapping("/{id}/changeProfile")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR')")
    public EmployeeDTO changeProfileOfEmployeeByAdmin(@RequestBody EmployeeDTO employeeDTO, @PathVariable Integer id){
        return employeeService.changeProfile(employeeDTO,id);
    }

    @PutMapping("/{leaveRequestId}/approve")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public LeaveRequestDTO approveLeaveRequest(@PathVariable Integer leaveRequestId){
        return employeeService.approveLeaveRequest(leaveRequestId);
    }

    @PutMapping("/{leaveRequestId}/decline")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public LeaveRequestDTO decline(@PathVariable Integer leaveRequestId){
        return employeeService.declineLeaveRequest(leaveRequestId);
    }

    @PutMapping("/{employeeId}/assign/{supervisorId}")
    @PreAuthorize("hasRole('HR')")
    public EmployeeDTO assignToSupervisor(@PathVariable Integer employeeId, @PathVariable Integer supervisorId){
        return employeeService.assignToSupervisor(employeeId, supervisorId);
    }

    @PutMapping("/{employeeId}/unassigned/{supervisorId}")
    @PreAuthorize("hasRole('HR')")
    public EmployeeDTO unassignedToSupervisor(@PathVariable Integer employeeId, @PathVariable Integer supervisorId){
        return employeeService.unassignedToSupervisor(employeeId, supervisorId);
    }

    @GetMapping("/{employeeId}/leaveRequestHistory")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public List<LeaveRequestDTO> leaveRequestHistoryOfEmployee(@PathVariable Integer employeeId){
        return employeeService.requestHistoryOfEmployee(employeeId);
    }
}
