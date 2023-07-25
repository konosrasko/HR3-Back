package com.open3hr.adeies.services;

import com.open3hr.adeies.dto.EmployeeDTO;
import com.open3hr.adeies.entities.Employee;

import java.util.List;

public interface EmployeeService {
     List<EmployeeDTO> findAllEmployees();

     EmployeeDTO findEmployeeById (Long id);

    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

    void deleteById(Long id);
}
