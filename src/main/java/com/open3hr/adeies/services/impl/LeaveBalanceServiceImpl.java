package com.open3hr.adeies.services.impl;

import com.open3hr.adeies.dto.LeaveBalanceDTO;
import com.open3hr.adeies.entities.Employee;
import com.open3hr.adeies.entities.LeaveBalance;
import com.open3hr.adeies.repositories.EmployeeRepository;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<LeaveBalanceDTO> findAll() {
        return leaveBalanceRepository.findAll().stream()
                .map(LeaveBalanceDTO::new)
                .toList();
    }

    @Override
    public LeaveBalanceDTO findById(Integer id) {
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
    public void deleteById(Integer id) {
        this.leaveBalanceRepository.findById(id).orElseThrow(()-> new RuntimeException("Leave balance with id "+ id +" not found"));
    }

    @Override
    public void addLeaveBalanceToEmployee(LeaveBalanceDTO leaveBalanceDTO, Integer employeeId) {
        Optional<Employee> foundEmployee = employeeRepository.findById(employeeId);
        if(foundEmployee.isPresent()){
            LeaveBalance myLeaveBalance = new LeaveBalance(leaveBalanceDTO, foundEmployee.get());
            leaveBalanceRepository.save(myLeaveBalance);
        } else {
            throw new RuntimeException("Couldn't find this employee to assign the leave balance");
        }
    }
}
