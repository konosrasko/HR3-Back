package com.open3hr.adeies.controllers;

import com.open3hr.adeies.dto.EmployeeDTO;
import com.open3hr.adeies.services.EmployeeService;
import com.open3hr.adeies.services.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")

public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping("/employees")
    public List<EmployeeDTO> getAllEmployees(){
        return employeeService.findAllEmployees();
    }

    @GetMapping("/employee/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable int id){
        return employeeService.findEmployeeById(id);
    }

    @PostMapping("/employee")
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employeeDTO){
         return employeeService.addEmployee(employeeDTO);
    }



}
