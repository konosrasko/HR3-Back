package com.open3hr.adeies.app.leaveRequest.service;

import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.dto.SubordinatesReqDTO;

import java.util.List;

public interface LeaveRequestService {


    List<LeaveRequestDTO> findAll();

    LeaveRequestDTO findById(Integer id);

    void deleteById(Integer id);

    List<LeaveRequestDTO> findRequestsForAnEmployee(int id);

    List<LeaveRequestDTO> getPendingRequests();

    List<SubordinatesReqDTO> getSubordinatesReq(Integer supervisorId);

}
