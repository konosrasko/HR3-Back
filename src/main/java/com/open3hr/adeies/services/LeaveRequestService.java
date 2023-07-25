package com.open3hr.adeies.services;

import com.open3hr.adeies.dto.LeaveBalanceDTO;
import com.open3hr.adeies.dto.LeaveRequestDTO;

import java.util.List;

public interface LeaveRequestService {


    List<LeaveRequestDTO> findAll();

    LeaveRequestDTO findById(Integer id);

    void deleteById(Integer id);

}
