package com.open3hr.adeies.app.leaveBalance.service;

import com.open3hr.adeies.app.leaveBalance.dto.LeaveBalanceDTO;

import java.util.List;

public interface LeaveBalanceService {

    List<LeaveBalanceDTO> findAll();

    LeaveBalanceDTO findById(Integer id);

    void editLeaveBalanceOfEmployee(LeaveBalanceDTO balanceDTO, Integer employeeId);

    void deleteLeaveBalanceOfEmployee(int employeeId, int leaveBalanceId);

    void addLeaveBalanceToEmployee(LeaveBalanceDTO leaveBalanceDTO, Integer employeeId);

    List<LeaveBalanceDTO> showBalancesOfEmployee(int employeeId);
}
