package com.open3hr.adeies.app.leaveRequest.controller;

import com.open3hr.adeies.app.employee.service.EmployeeService;
import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;
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
    private EmployeeService employeeService;
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
    public ResponseEntity<LeaveRequestDTO> deleteLeaveRequest(@PathVariable Integer id){
        return new ResponseEntity<>(leaveRequestService.deleteRequestById(id),HttpStatus.NO_CONTENT);
    }

    //used in: http://localhost:4200/home/leaves/edit
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<LeaveRequestDTO> findLeaveRequest(@PathVariable Integer id){
        return new ResponseEntity<>(leaveRequestService.findById(id),HttpStatus.OK);
    }

    //used in: http://localhost:4200/home/leaves/edit
    @PutMapping("")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<LeaveRequestDTO> updateLeaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO){
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        int employeeId = userService.getUserInfo(loggedUsername).getEmployeeId();
        return new ResponseEntity<>(leaveRequestService.editLeaveRequest(leaveRequestDTO, employeeId),HttpStatus.OK);
    }

    //used in: http://localhost:4200/home/subordinates/requests
    @GetMapping("/direct-subordinates")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public ResponseEntity<List<SubordinatesReqDTO>> findDirectSubordinatesReq(){
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        int supervisorId = userService.getUserInfo(loggedUsername).getEmployeeId();
        return new ResponseEntity<>(leaveRequestService.getSubordinatesReq(supervisorId),HttpStatus.OK);
    }

    //used in: http://localhost:4200/home/subordinates/requests
    @GetMapping("/all-subordinates")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public ResponseEntity<List<SubordinatesReqDTO>> findAllSubordinatesReq(){
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        int supervisorId = userService.getUserInfo(loggedUsername).getEmployeeId();
        return new ResponseEntity<>(leaveRequestService.getAllSubordinatesReq(supervisorId, employeeService.findAllSubordinates(supervisorId)),HttpStatus.OK);
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


    @GetMapping("/pending")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<List<LeaveRequestDTO>> getPendingRequest(){
        return new ResponseEntity<>(leaveRequestService.getPendingRequests(),HttpStatus.OK);
    }

}
