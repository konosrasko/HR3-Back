package com.open3hr.adeies.services;

import com.open3hr.adeies.dto.EmployeeDTO;
import com.open3hr.adeies.dto.LeaveBalanceDTO;
import com.open3hr.adeies.dto.LeaveRequestDTO;
import com.open3hr.adeies.entities.Employee;
import com.open3hr.adeies.entities.LeaveRequest;

import java.util.List;

public interface EmployeeService {
     List<EmployeeDTO> findAllEmployees();

     EmployeeDTO findEmployeeById (Integer id);

    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

    void deleteById(Integer id);

    LeaveRequestDTO addLeaveRequest(LeaveRequestDTO leaveRequestDTO,int employeeId);
}
