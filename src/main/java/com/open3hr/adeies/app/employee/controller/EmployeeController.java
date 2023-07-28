package com.open3hr.adeies.app.employee.controller;


import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.employee.service.EmployeeService;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<EmployeeDTO> getAllEmployees(){
        return employeeService.findAllEmployees();
    }

    @GetMapping("/employee/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Integer id){
        return employeeService.findEmployeeById(id);
    }

    @PostMapping("/employee")
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeService.addEmployee(employeeDTO);
    }

    @DeleteMapping("/employee/{id}")
    public void deleteById(@PathVariable Integer id){
        employeeService.deleteById(id);
    }

    @PostMapping("/employee/{id}/leaveRequest")
    public LeaveRequestDTO leaveRequestDTO(@RequestBody LeaveRequestDTO leaveRequestDTO, @PathVariable Integer id ){
        return employeeService.addLeaveRequest(leaveRequestDTO,id);
    }

    @PostMapping("/employee/withoutAccount")
    public List<EmployeeDTO> employeesWithoutAccount(){
        return employeeService.employeesWithoutAccount();
    }

}
