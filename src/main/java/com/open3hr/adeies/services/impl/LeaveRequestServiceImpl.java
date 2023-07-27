package com.open3hr.adeies.services.impl;

import com.open3hr.adeies.dto.EmployeeDTO;
import com.open3hr.adeies.dto.LeaveBalanceDTO;
import com.open3hr.adeies.dto.LeaveCategoryDTO;
import com.open3hr.adeies.dto.LeaveRequestDTO;
import com.open3hr.adeies.entities.Employee;
import com.open3hr.adeies.entities.LeaveCategory;
import com.open3hr.adeies.entities.LeaveRequest;
import com.open3hr.adeies.repositories.EmployeeRepository;
import com.open3hr.adeies.repositories.LeaveCategoryRepository;
import com.open3hr.adeies.repositories.LeaveRequestRepository;
import com.open3hr.adeies.services.EmployeeService;
import com.open3hr.adeies.services.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveCategoryRepository categoryRepository;


    @Override
    public List<LeaveRequestDTO> findAll() {
        return leaveRequestRepository.findAll()
                .stream()
                .map(leaveRequest -> {
                    Optional<LeaveCategory> category = categoryRepository.findCategoryByTitle(leaveRequest.getCategory().getTitle());
                    if(category.isPresent()){
                        return new LeaveRequestDTO(leaveRequest, category.get());
                    }else throw new RuntimeException("Error with leave request");
                })
                .toList();
    }

    @Override
    public LeaveRequestDTO findById(Integer id) {
        Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findById(id);
        if (leaveRequest.isPresent()) {
            Optional<LeaveCategory> category = categoryRepository.findCategoryByTitle(leaveRequest.get().getCategory().getTitle());
            if(category.isPresent()){
                return new LeaveRequestDTO(leaveRequest.get(), category.get());
            }else throw new RuntimeException("Error Finding Leave Category");
        } else throw new RuntimeException("Couldn't find request with id: " + id);
    }

    @Override
    public void deleteById(Integer id) {
        this.leaveRequestRepository.deleteById(id);
    }

    @Override
    public List<LeaveRequestDTO> findRequestsForAnEmployee(int id) {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
        return leaveRequests.stream()
                .filter(leaveRequest -> leaveRequest.getEmployee().getId() == id)
                .map(leaveRequest -> {
                    Optional<LeaveCategory> category = categoryRepository.findCategoryByTitle(leaveRequest.getCategory().getTitle());
                    if (category.isPresent()){
                        return new LeaveRequestDTO(leaveRequest, category.get());
                    }else throw new RuntimeException("Error with leave category");
                })
                .collect(Collectors.toList());
    }
}