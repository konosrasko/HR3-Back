package com.open3hr.adeies.app.leaveBalance.service;

import com.open3hr.adeies.app.leaveBalance.dto.LeaveBalanceDTO;

import java.util.List;

public interface LeaveBalanceService {

    List<LeaveBalanceDTO> findAll();

    LeaveBalanceDTO findById(Integer id);

    //LeaveBalanceDTO save(LeaveBalanceDTO LeaveBalanceDTO);

    void deleteById(Integer id);

    void addLeaveBalanceToEmployee(LeaveBalanceDTO leaveBalanceDTO, Integer employeeId);

    List<LeaveBalanceDTO> showBalanceOfEmployee(int employeeId);
}