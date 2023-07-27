package com.open3hr.adeies.app.leaveBalance.controller;

import com.open3hr.adeies.app.leaveBalance.dto.LeaveBalanceDTO;
import com.open3hr.adeies.app.leaveBalance.service.LeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
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

    @GetMapping("/employee/{id}/leavebalance")
    public List<LeaveBalanceDTO> getAllLeaveBalancesOfAnEmployee(@PathVariable Integer id){
        return leaveBalanceService.showBalanceOfEmployee(id);
    }

    @PostMapping("/employee/{id}/leavebalance")
    public String addLeaveBalanceToAnEmployee(@PathVariable Integer id, @RequestBody LeaveBalanceDTO leaveBalanceDTO){
        leaveBalanceService.addLeaveBalanceToEmployee(leaveBalanceDTO, id);
        return ("New balance added to employee with id " + id);
    }

    @DeleteMapping("/leavebalance/{id}")
    public void deleteById(@PathVariable Integer id){
        leaveBalanceService.deleteById(id);
    }


//    @PostMapping("/leavebalance")
//    public LeaveBalanceDTO save(@RequestBody LeaveBalanceDTO leaveBalanceDTO){
//        leaveBalanceDTO.setId(0);
//        return leaveBalanceService.save(leaveBalanceDTO);
//    }

//    @PutMapping("/leavebalance")
//    public LeaveBalanceDTO update(@RequestBody LeaveBalanceDTO leaveBalanceDTO){
//        return leaveBalanceService.save(leaveBalanceDTO);
//    }

}
