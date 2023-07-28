package com.open3hr.adeies.app.employee.controller;


import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.employee.service.EmployeeService;
import com.open3hr.adeies.app.leaveBalance.dto.LeaveBalanceDTO;
import com.open3hr.adeies.app.leaveBalance.service.LeaveBalanceService;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    @GetMapping("")
    public List<EmployeeDTO> getAllEmployees(){
        return employeeService.findAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Integer id){
        return employeeService.findEmployeeById(id);
    }

    @PostMapping("")
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeService.addEmployee(employeeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        employeeService.deleteById(id);
    }

    @PostMapping("/{id}/leaveRequest")
    public LeaveRequestDTO leaveRequestDTO(@RequestBody LeaveRequestDTO leaveRequestDTO, @PathVariable int id ){
        return employeeService.addLeaveRequest(leaveRequestDTO,id);
    }

    @PostMapping("/withoutAccount")
    public List<EmployeeDTO> employeesWithoutAccount(){
        return employeeService.employeesWithoutAccount();
    }


    @GetMapping("/{id}/leavebalance")
    public List<LeaveBalanceDTO> getAllLeaveBalancesOfAnEmployee(@PathVariable Integer id){
        return leaveBalanceService.showBalanceOfEmployee(id);
    }

    @PostMapping("/{id}/leavebalance")
    public String addLeaveBalanceToAnEmployee(@PathVariable Integer id, @RequestBody LeaveBalanceDTO leaveBalanceDTO){
        leaveBalanceService.addLeaveBalanceToEmployee(leaveBalanceDTO, id);
        return ("New balance added to employee with id " + id);
    }

    @PutMapping("/{id}/changeProfile")
    public EmployeeDTO changeProfileOfEmployeeByAdmin(@RequestBody EmployeeDTO employeeDTO, @PathVariable Integer id){
        // make checks when security is added for admin
        return employeeService.changeProfile(employeeDTO,id);
    }

    @PutMapping("/{employeeId}/approve/{leaveRequestId}")
    public LeaveRequestDTO approveLeaveRequest(@PathVariable Integer employeeId, @PathVariable Integer leaveRequestId){
        return employeeService.acceptLeaveRequest(employeeId,leaveRequestId);
    }

    @PutMapping("/{employeeId}/reject/{leaveRequestId}")
    public LeaveRequestDTO denyLeaveRequest(@PathVariable Integer employeeId, @PathVariable Integer leaveRequestId){
        return employeeService.denyLeaveRequest(employeeId,leaveRequestId);
    }

    @PutMapping("/{employeeId}/assign/{supervisorId}")
    public EmployeeDTO assignToSupervisor(@PathVariable Integer employeeId, @PathVariable Integer supervisorId){
        return employeeService.assignToSupervisor(employeeId, supervisorId);
    }

    @PutMapping("/{employeeId}/unassigned/{supervisorId}")
    public EmployeeDTO unassignedToSupervisor(@PathVariable Integer employeeId, @PathVariable Integer supervisorId){
        return employeeService.unassignedToSupervisor(employeeId, supervisorId);
    }
}
