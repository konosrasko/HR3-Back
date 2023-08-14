package com.open3hr.adeies.app.leaveBalance.controller;

import com.open3hr.adeies.app.leaveBalance.dto.LeaveBalanceDTO;
import com.open3hr.adeies.app.leaveBalance.service.LeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leavebalance")
public class LeaveBalanceController {

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    @GetMapping("")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<List<LeaveBalanceDTO>> findAll(){
        return new ResponseEntity<>(leaveBalanceService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<LeaveBalanceDTO> findById(@PathVariable Integer id){
        return new ResponseEntity<>(leaveBalanceService.findById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity deleteById(@PathVariable Integer id){
        leaveBalanceService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
