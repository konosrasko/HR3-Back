package com.open3hr.adeies.app.employee.service;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> findAllEmployees();

    EmployeeDTO findEmployeeById (Integer id);

    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

    void deleteById(Integer id);

    LeaveRequestDTO addLeaveRequest(LeaveRequestDTO leaveRequestDTO, Integer employeeId);

    List<EmployeeDTO> employeesWithoutAccount();

    LeaveRequestDTO acceptLeaveRequest(Integer employeeId, Integer leaveRequestId);

    LeaveRequestDTO denyLeaveRequest(Integer employeeId, Integer leaveRequestId);

    EmployeeDTO changeProfile(EmployeeDTO employeeDTO, Integer id);

    EmployeeDTO assignToSupervisor(Integer employeeId, Integer supervisorId);

    EmployeeDTO unassignedToSupervisor(Integer employeeId, Integer supervisorId);

}
