package com.open3hr.adeies.services;

import com.open3hr.adeies.dto.LeaveBalanceDTO;

import java.util.List;

public interface LeaveBalanceService {

    List<LeaveBalanceDTO> findAll();

    LeaveBalanceDTO findById(Integer id);

    LeaveBalanceDTO save(LeaveBalanceDTO LeaveBalanceDTO);

    void deleteById(Integer id);
}
