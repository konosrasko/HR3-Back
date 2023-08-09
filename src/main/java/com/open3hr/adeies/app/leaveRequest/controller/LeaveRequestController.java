package com.open3hr.adeies.app.leaveRequest.controller;

import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.service.LeaveRequestService;
import com.open3hr.adeies.app.leaveRequest.dto.SubordinatesReqDTO;
import com.open3hr.adeies.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/leaverequests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private UserService userService;

    @GetMapping("/searchemployeeleaverequest/{id}")
    @PreAuthorize("hasRole('HR')")
    public List<LeaveRequestDTO> getAllLeaveRequestsOfAnEmployee(@PathVariable Integer id) {
        return leaveRequestService.findRequestsForAnEmployee(id);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public List<LeaveRequestDTO> getAllLeaveRequest(){
        return leaveRequestService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public LeaveRequestDTO findLeaveRequestDTO(@PathVariable Integer id){
        return leaveRequestService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public void deleteRequest(@PathVariable Integer id){
        leaveRequestService.deleteById(id);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public List<LeaveRequestDTO> getPendingRequest(){
        return leaveRequestService.getPendingRequests();
    }


    @GetMapping("/supervisor")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public List<SubordinatesReqDTO> findSubordinatesReq(){
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        int supervisorId = userService.getUserInfo(loggedUsername).getEmployeeId();
        return leaveRequestService.getSubordinatesReq(supervisorId);
    }

}
