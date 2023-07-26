package com.open3hr.adeies.controllers;

import com.open3hr.adeies.dto.EmployeeDTO;
import com.open3hr.adeies.dto.LeaveRequestDTO;
import com.open3hr.adeies.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
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
    public LeaveRequestDTO leaveRequestDTO(@RequestBody LeaveRequestDTO leaveRequestDTO, @PathVariable int id ){
        return employeeService.addLeaveRequest(leaveRequestDTO,id);
    }

}