package com.open3hr.adeies.controllers;

import com.open3hr.adeies.dto.LeaveBalanceDTO;
import com.open3hr.adeies.dto.LeaveRequestDTO;
import com.open3hr.adeies.entities.LeaveRequest;
import com.open3hr.adeies.services.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping("/leaverequest/add")
    public  LeaveRequest leaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO){
        return new LeaveRequest(leaveRequestService.save(leaveRequestDTO));
    }


    @GetMapping("/leaverequest/{employeeId}")
    public List<LeaveRequestDTO> getAllLeaveRequestsOfAnEmployee(@PathVariable int id)
    {
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
