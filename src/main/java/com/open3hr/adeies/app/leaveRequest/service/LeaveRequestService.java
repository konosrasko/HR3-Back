package com.open3hr.adeies.app.leaveRequest.service;

import com.open3hr.adeies.app.employee.dto.EmployeeSupervisorDTO;
import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.dto.SubordinatesReqDTO;

import java.util.List;

public interface LeaveRequestService {


    List<LeaveRequestDTO> findAll();

    LeaveRequestDTO findById(Integer id);

    LeaveRequestDTO deleteRequestById(Integer id);

    List<LeaveRequestDTO> findRequestsForAnEmployee(int id);

    List<LeaveRequestDTO> getPendingRequests();

    LeaveRequestDTO editLeaveRequest(LeaveRequestDTO leaveRequestDTO, Integer employeeId);

    List<SubordinatesReqDTO> getSubordinatesReq(Integer supervisorId);

    List<SubordinatesReqDTO> getAllSubordinatesReq(Integer supervisorId, List<EmployeeSupervisorDTO> subordinates);
}
