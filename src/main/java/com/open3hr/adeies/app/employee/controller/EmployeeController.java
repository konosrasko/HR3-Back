package com.open3hr.adeies.app.employee.controller;


import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.employee.dto.EmployeeSupervisorDTO;
import com.open3hr.adeies.app.employee.dto.miniEmployeeDTO;
import com.open3hr.adeies.app.employee.service.EmployeeService;
import com.open3hr.adeies.app.leaveBalance.dto.LeaveBalanceDTO;
import com.open3hr.adeies.app.leaveBalance.service.LeaveBalanceService;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<LeaveRequestDTO> newLeaveRequestForLoggedUser(@RequestBody LeaveRequestDTO leaveRequestDTO){
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        int id = userService.getUserInfo(loggedUsername).getEmployeeId();
        return new ResponseEntity<>(employeeService.addLeaveRequest(leaveRequestDTO,id), HttpStatus.CREATED);
    }

    //used in: http://localhost:4200/home/leaves/add (by HR only)
    @PostMapping("/{id}/leaverequests/add")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<LeaveRequestDTO> newLeaveRequestForEmployee(@RequestBody LeaveRequestDTO leaveRequestDTO, @PathVariable Integer id){
        return new ResponseEntity<>(employeeService.addLeaveRequest(leaveRequestDTO,id),HttpStatus.CREATED);
    }

    //used in: http://localhost:4200/home/leaves/add
    @GetMapping("/balance")
    @PreAuthorize("hasRole('HR') OR hasRole('Admin') OR hasRole('Employee')")
    public ResponseEntity<List<LeaveBalanceDTO>> getLeaveBalanceOfLoggedUser(){
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        int id = userService.getUserInfo(loggedUsername).getEmployeeId();
        return new ResponseEntity<>(leaveBalanceService.showBalancesOfEmployee(id),HttpStatus.OK);
    }

    //used in: http://localhost:4200/home/leaves/add (by HR only)
    @GetMapping("/{id}/balance")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<LeaveBalanceDTO>> getLeaveBalancesOfEmployee(@PathVariable Integer id){
        return new ResponseEntity<>(leaveBalanceService.showBalancesOfEmployee(id),HttpStatus.OK);
    }

    //used in: http://localhost:4200/home/subordinates
    @GetMapping("direct-subordinates")
    @PreAuthorize("hasRole('HR') OR hasRole('Admin') OR hasRole('Employee')")
    public ResponseEntity<List<EmployeeSupervisorDTO>> getDirectSubordinates(){
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        int id = userService.getUserInfo(loggedUsername).getEmployeeId();
        return new ResponseEntity<>(employeeService.findAllDirectSubordinates(id), HttpStatus.OK);
    }

    //used in: http://localhost:4200/home/subordinates
    @GetMapping("all-subordinates")
    @PreAuthorize("hasRole('HR') OR hasRole('Admin') OR hasRole('Employee')")
    public ResponseEntity<List<EmployeeSupervisorDTO>> getAllSubordinates(){
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        int id = userService.getUserInfo(loggedUsername).getEmployeeId();
        return new ResponseEntity<>(employeeService.findAllSubordinates(id), HttpStatus.OK);
    }


    /* -------- v Undocumented v -------- */
    /* ---------------------------------- */
    @GetMapping("")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR')")
    public ResponseEntity<List<EmployeeSupervisorDTO>> getAllEmployees(){
        return new ResponseEntity<>(employeeService.findAllEmployees(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR')")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer id){
        return new ResponseEntity<>(employeeService.findEmployeeById(id),HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO){
        return new ResponseEntity<>(employeeService.addEmployee(employeeDTO),HttpStatus.CREATED);
    }

    @GetMapping("/withoutAccount")
    @PreAuthorize("hasRole('HR') OR hasRole('Admin')")
    public ResponseEntity<List<EmployeeDTO>> employeesWithoutAccount(){
        return new ResponseEntity<>(employeeService.employeesWithoutAccount(),HttpStatus.OK);
    }

    @PostMapping("/{id}/leavebalance")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity addLeaveBalanceToAnEmployee(@PathVariable Integer id, @RequestBody LeaveBalanceDTO leaveBalanceDTO){
        leaveBalanceService.addLeaveBalanceToEmployee(leaveBalanceDTO, id);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/changeProfile")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<EmployeeDTO> changeProfileOfEmployeeByAdmin(@RequestBody EmployeeDTO employeeDTO, @PathVariable Integer id){
        return new ResponseEntity<>(employeeService.changeProfile(employeeDTO,id),HttpStatus.OK);
    }

    @PutMapping("/{leaveRequestId}/approve")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public ResponseEntity<LeaveRequestDTO> approveLeaveRequest(@PathVariable Integer leaveRequestId){
        return new ResponseEntity<>(employeeService.approveLeaveRequest(leaveRequestId),HttpStatus.OK);
    }

    @PutMapping("/{leaveRequestId}/decline")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public ResponseEntity<LeaveRequestDTO> decline(@PathVariable Integer leaveRequestId){
        return new ResponseEntity<>(employeeService.declineLeaveRequest(leaveRequestId),HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/assign/{supervisorId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<EmployeeDTO> assignToSupervisor(@PathVariable Integer employeeId, @PathVariable Integer supervisorId){
        return new ResponseEntity<>(employeeService.assignToSupervisor(employeeId, supervisorId),HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/unassigned/{supervisorId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<EmployeeDTO> unassignedToSupervisor(@PathVariable Integer employeeId, @PathVariable Integer supervisorId){
        return new ResponseEntity<>(employeeService.unassignedToSupervisor(employeeId, supervisorId),HttpStatus.OK);
    }

    @GetMapping("/{employeeId}/leaveRequestHistory")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<List<LeaveRequestDTO>> leaveRequestHistoryOfEmployee(@PathVariable Integer employeeId){
        return new ResponseEntity<>(employeeService.requestHistoryOfEmployee(employeeId),HttpStatus.OK);
    }

    @GetMapping("/allSupervisors")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<miniEmployeeDTO>> supervisorsLastNames() {
        return new ResponseEntity<>(employeeService.findAllSupervisors(),HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}/leavebalance/{leaveBalanceId}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR')")
    public ResponseEntity deleteLeave(@PathVariable Integer employeeId, @PathVariable Integer leaveBalanceId){
        leaveBalanceService.deleteLeaveBalanceOfEmployee(employeeId, leaveBalanceId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
