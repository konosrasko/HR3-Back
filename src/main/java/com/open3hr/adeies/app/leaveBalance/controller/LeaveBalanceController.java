package com.open3hr.adeies.app.leaveBalance.controller;

import com.open3hr.adeies.app.leaveBalance.dto.LeaveBalanceDTO;
import com.open3hr.adeies.app.leaveBalance.service.LeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<LeaveBalanceDTO> findAll(){
        return leaveBalanceService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public LeaveBalanceDTO findById(@PathVariable Integer id){
        return leaveBalanceService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public void deleteById(@PathVariable Integer id){
        leaveBalanceService.deleteById(id);
    }

}
