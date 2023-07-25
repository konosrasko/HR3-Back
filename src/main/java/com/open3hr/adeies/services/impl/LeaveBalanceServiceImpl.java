package com.open3hr.adeies.services.impl;

import com.open3hr.adeies.dto.LeaveBalanceDTO;
import com.open3hr.adeies.entities.LeaveBalance;
import com.open3hr.adeies.repositories.LeaveBalanceRepository;
import com.open3hr.adeies.services.LeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService {
    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Override
    public List<LeaveBalanceDTO> findAll() {
        return leaveBalanceRepository.findAll().stream()
                .map(LeaveBalanceDTO::new)
                .toList();
    }

    @Override
    public LeaveBalanceDTO findById(Long id) {
        Optional<LeaveBalance> myLeaveBalance = leaveBalanceRepository.findById(id);
        if(myLeaveBalance.isPresent()){
            return new LeaveBalanceDTO(myLeaveBalance.get());
        } else {
            throw new RuntimeException("Couldn't find leave balance with id: "+ id);
        }
    }

    @Override
    public LeaveBalanceDTO save(LeaveBalanceDTO leaveBalanceDTO) {
        LeaveBalance myLeaveBalance = new LeaveBalance(leaveBalanceDTO);
        return new LeaveBalanceDTO(leaveBalanceRepository.save(myLeaveBalance));
    }

    @Override
    public void deleteById(Long id) {
        this.leaveBalanceRepository.findById(id).orElseThrow(()-> new RuntimeException("Leave balance with id "+ id +" not found"));
    }
}
