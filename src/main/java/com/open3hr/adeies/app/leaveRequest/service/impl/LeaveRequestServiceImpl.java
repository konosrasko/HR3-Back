package com.open3hr.adeies.app.leaveRequest.service.impl;

import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.exceptions.BadDataException;
import com.open3hr.adeies.app.exceptions.ConflictException;
import com.open3hr.adeies.app.exceptions.NotFoundException;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.dto.SubordinatesReqDTO;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.leaveRequest.repository.LeaveRequestRepository;
import com.open3hr.adeies.app.leaveRequest.service.LeaveRequestService;
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
                    if (category.isPresent()) {
                        return new LeaveRequestDTO(leaveRequest, category.get());
                    } else throw new NotFoundException("Error with leave request");
                })
                .toList();
    }

    @Override
    public LeaveRequestDTO findById(Integer id) {
        Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findById(id);
        if (leaveRequest.isPresent()) {
            Optional<LeaveCategory> category = categoryRepository.findCategoryByTitle(leaveRequest.get().getCategory().getTitle());
            if (category.isPresent()) {
                return new LeaveRequestDTO(leaveRequest.get(), category.get());
            } else throw new BadDataException("Error Finding Leave Category");
        } else throw new NotFoundException("Couldn't find leave request with id: " + id);
    }

    //Είναι απαίσιο, το ξέρω :(
    @Override
    public LeaveRequestDTO deleteRequestById(Integer id) {
        Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findById(id);
        if (leaveRequest.isPresent() && leaveRequest.get().getStatus() == Status.PENDING) {
        try {
            leaveRequest.get().getEmployee().findBalanceOfCategory(leaveRequest.get().getCategory()).addDaysTaken(-leaveRequest.get().getDuration());
            try {
                this.leaveRequestRepository.deleteByIdNative(id);
            } catch (Exception e) {
                System.out.println("Delete failed: " + e.getMessage() + e.getCause());
            }
            return new LeaveRequestDTO(leaveRequest.get(), leaveRequest.get().getCategory());
        } catch (Exception e) {
            throw new ConflictException("Employee doesn't have a matching leave balance to the request");
        }
        } else throw new BadDataException("This leave request is not deletable");
    }

    @Override
    public List<LeaveRequestDTO> findRequestsForAnEmployee(int id) {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
        return leaveRequests.stream()
                .filter(leaveRequest -> leaveRequest.getEmployee().getId() == id)
                .map(leaveRequest -> {
                    Optional<LeaveCategory> category = categoryRepository.findCategoryByTitle(leaveRequest.getCategory().getTitle());
                    if (category.isPresent()) {
                        return new LeaveRequestDTO(leaveRequest, category.get());
                    } else throw new BadDataException("Leave category missing from employee's leave balance");
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaveRequestDTO> getPendingRequests() {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();

        List<LeaveRequest> pendingRequests = new ArrayList<>();
        for (LeaveRequest leaveRequest : leaveRequests) {
            if (leaveRequest.getStatus() == Status.PENDING) {
                pendingRequests.add(leaveRequest);
            }
        }
        return pendingRequests
                .stream()
                .map(leaveRequest -> {
                    Optional<LeaveCategory> categoryOfRequest = categoryRepository.findById(leaveRequest.getCategory().getId());
                    if (categoryOfRequest.isPresent()) {
                        return new LeaveRequestDTO(leaveRequest, categoryOfRequest.get());
                    } else throw new NotFoundException("This category does not exists");
                })
                .toList();

    }


    @Override
    public List<SubordinatesReqDTO> getSubordinatesReq(Integer supervisorId) {

        List<LeaveRequest> myLeaveRequestHistory = leaveRequestRepository.findLeaveRequestsForSupervisedEmployees(supervisorId);
        return myLeaveRequestHistory.stream()
                .map(leaveRequest -> new SubordinatesReqDTO(leaveRequest, leaveRequest.getEmployee()))
                .toList();
    }
}





