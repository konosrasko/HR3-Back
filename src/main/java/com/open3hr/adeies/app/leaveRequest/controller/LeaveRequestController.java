package com.open3hr.adeies.app.leaveRequest.controller;

import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.dto.SubordinatesReqDTO;
import com.open3hr.adeies.app.leaveRequest.service.LeaveRequestService;
import com.open3hr.adeies.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //used in: http://localhost:4200/home/leaves/requests
    @GetMapping("/all")
    @PreAuthorize("hasRole('HR') OR hasRole('Admin') OR hasRole('Employee')")
    public ResponseEntity<List<LeaveRequestDTO>> getAllMyLeaveRequests() {
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        int id = userService.getUserInfo(loggedUsername).getEmployeeId();
        return new ResponseEntity<>(leaveRequestService.findRequestsForAnEmployee(id), HttpStatus.OK);
    }

    //used in: http://localhost:4200/home/leaves/requests
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<LeaveRequestDTO> deleteRequest(@PathVariable Integer id){
        return new ResponseEntity<>(leaveRequestService.deleteRequestById(id),HttpStatus.NO_CONTENT);
    }




    /* -------- v Undocumented v -------- */
    /* ---------------------------------- */

    @GetMapping("/searchemployeeleaverequest/{id}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequestsOfAnEmployee(@PathVariable Integer id) {
        return new ResponseEntity<>(leaveRequestService.findRequestsForAnEmployee(id),HttpStatus.OK);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequest(){
        return new ResponseEntity<>(leaveRequestService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<LeaveRequestDTO> findLeaveRequestDTO(@PathVariable Integer id){
        return new ResponseEntity<>(leaveRequestService.findById(id),HttpStatus.OK);
    }



    @GetMapping("/pending")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<List<LeaveRequestDTO>> getPendingRequest(){
        return new ResponseEntity<>(leaveRequestService.getPendingRequests(),HttpStatus.OK);
    }


    @GetMapping("/supervisor")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public ResponseEntity<List<SubordinatesReqDTO>> findSubordinatesReq(){
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        int supervisorId = userService.getUserInfo(loggedUsername).getEmployeeId();
        return new ResponseEntity<>(leaveRequestService.getSubordinatesReq(supervisorId),HttpStatus.OK);
    }

}
