package com.open3hr.adeies.app.leaveRequest.controller;

import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/leaverequests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping("/searchemployeeleaverequest/{id}")
    public List<LeaveRequestDTO> getAllLeaveRequestsOfAnEmployee(@PathVariable int id) {
        return leaveRequestService.findRequestsForAnEmployee(id);
    }

    @GetMapping("")
    public List<LeaveRequestDTO> getAllLeaveRequest(){
        return leaveRequestService.findAll();
    }

    @GetMapping("/{id}")
    public LeaveRequestDTO leaveRequestDTO(@PathVariable Integer id){
        return leaveRequestService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable Integer id){
        leaveRequestService.deleteById(id);
    }

    @GetMapping("/pending")
    public List<LeaveRequestDTO> getPendingRequest(){
        return leaveRequestService.getPendingRequests();
    }
}
