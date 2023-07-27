package com.open3hr.adeies.app.employee.service;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> findAllEmployees();

    EmployeeDTO findEmployeeById (Integer id);

    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

    void deleteById(Integer id);

    LeaveRequestDTO addLeaveRequest(LeaveRequestDTO leaveRequestDTO, int employeeId);

    List<EmployeeDTO> employeesWithoutAccount();

    EmployeeDTO changeProfile(EmployeeDTO employeeDTO, Integer id);
}
