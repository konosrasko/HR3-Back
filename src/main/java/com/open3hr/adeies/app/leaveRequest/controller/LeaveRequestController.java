package com.open3hr.adeies.app.leaveRequest.controller;

import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/leaves")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping("/searchemployeeleaverequest/{id}")
    public List<LeaveRequestDTO> getAllLeaveRequestsOfAnEmployee(@PathVariable int id) {
        return leaveRequestService.findRequestsForAnEmployee(id);
    }

    @GetMapping("/leaverequest/all")
    public List<LeaveRequestDTO> getAllLeaveRequest(){
        return leaveRequestService.findAll();
    }

    @GetMapping("/leaverequest/{id}")
    public LeaveRequestDTO leaveRequestDTO(@PathVariable Integer id){
        return leaveRequestService.findById(id);
    }

    @DeleteMapping("/leaverequest/{id}")
    public void deleteRequest(@PathVariable Integer id){
        leaveRequestService.deleteById(id);
    }
}
