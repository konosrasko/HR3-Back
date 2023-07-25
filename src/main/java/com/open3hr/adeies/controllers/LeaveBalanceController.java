package com.open3hr.adeies.controllers;

import com.open3hr.adeies.dto.LeaveBalanceDTO;
import com.open3hr.adeies.services.LeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaveBalanceController {

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    @GetMapping("/leavebalance")
    public List<LeaveBalanceDTO> findAll(){
        return leaveBalanceService.findAll();
    }

    @GetMapping("/leavebalance/{id}")
    public LeaveBalanceDTO findById(@PathVariable Integer id){
        return leaveBalanceService.findById(id);
    }

    @PostMapping("/leavebalance")
    public LeaveBalanceDTO save(@RequestBody LeaveBalanceDTO leaveBalanceDTO){
        leaveBalanceDTO.setId(0);
        return leaveBalanceService.save(leaveBalanceDTO);
    }

    @PutMapping("/leavebalance")
    public LeaveBalanceDTO update(@RequestBody LeaveBalanceDTO leaveBalanceDTO){
        return leaveBalanceService.save(leaveBalanceDTO);
    }

    @DeleteMapping("/leavebalance/{id}")
    public void deleteById(@PathVariable Integer id){
        leaveBalanceService.deleteById(id);
    }
}
