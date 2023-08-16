package com.open3hr.adeies.app.employee.service;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.employee.dto.EmployeeSupervisorDTO;
import com.open3hr.adeies.app.employee.dto.miniEmployeeDTO;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;

public interface EmployeeService {

    List<EmployeeSupervisorDTO> findAllEmployees();

    EmployeeDTO findEmployeeById (Integer id);

    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

    void deleteById(Integer id);

    LeaveRequestDTO addLeaveRequest(LeaveRequestDTO leaveRequestDTO, int employeeId);

    List<EmployeeDTO> employeesWithoutAccount();

    EmployeeDTO changeProfile(EmployeeDTO employeeDTO, Integer id);

    EmployeeDTO assignToSupervisor(Integer employeeId, Integer supervisorId);

    EmployeeDTO unassignedToSupervisor(Integer employeeId, Integer supervisorId);

    List<LeaveRequestDTO> requestHistoryOfEmployee(Integer employeeId);

    EmployeeDTO findEmployeeByUserName(String username);

    List<EmployeeSupervisorDTO> findAllDirectSubordinates(Integer supervisorId);

    List<EmployeeSupervisorDTO> findAllSubordinates(Integer supervisorId);

    List<miniEmployeeDTO> findAllSupervisors();

    LeaveRequestDTO approveLeaveRequest(Integer leaveReqId);

    LeaveRequestDTO declineLeaveRequest(Integer leaveReqId);

}
