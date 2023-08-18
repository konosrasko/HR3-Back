package com.open3hr.adeies.app.leaveRequest.service.impl;

import com.open3hr.adeies.app.employee.dto.EmployeeSupervisorDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.exceptions.BadDataException;
import com.open3hr.adeies.app.exceptions.ConflictException;
import com.open3hr.adeies.app.exceptions.NotFoundException;
import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveBalance.repository.LeaveBalanceRepository;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.dto.SubordinatesReqDTO;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.leaveRequest.repository.LeaveRequestRepository;
import com.open3hr.adeies.app.leaveRequest.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

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
                    } else throw new NotFoundException("Πρόβλημα με την αίτηση άδειας");
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
            } else throw new BadDataException("Πρόβλημα: η κατηγορία δεν βρέθηκε");
        } else throw new NotFoundException("Η αίτηση άδειας με id: " + id + "δεν βρέθηκε");
    }

    //Είναι απαίσιο, το ξέρω :(
    @Override
    public LeaveRequestDTO deleteRequestById(Integer id) {
        Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findById(id);
        if (leaveRequest.isPresent() && leaveRequest.get().getStatus() == Status.PENDING) {
            try {
                LeaveBalance leaveBalance = leaveRequest.get().getEmployee().findBalanceOfCategory(leaveRequest.get().getCategory());
                leaveBalance.addDaysTaken(-leaveRequest.get().getDuration());
                try {
                    this.leaveRequestRepository.deleteByIdNative(id);
                } catch (Exception e) {
                    System.out.println("Η διαγραφή απέτυχε: " + e.getMessage() + e.getCause());
                }
                return new LeaveRequestDTO(leaveRequest.get(), leaveRequest.get().getCategory());
            } catch (Exception e) {
                throw new ConflictException("Δεν αντιστοιχεί κάποια κατηγορία άδειας με το συγκεκριμένο αίτημα");
            }
        } else throw new BadDataException("Αυτό το αίτημα άδειας δεν μπορεί να διαγραφτεί");
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
                    } else
                        throw new BadDataException("Η συγκεκριμένη κατηγορία άδειας λείπει από τις δικαιούμενες άδειες του εργαζομένου");
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
                    } else throw new NotFoundException("Η συγκεκριμένη κατηγορία άδειας δεν υπάρχει");
                })
                .toList();

    }

    @Override
    public LeaveRequestDTO editLeaveRequest(LeaveRequestDTO leaveRequestDTO, Integer employeeId) {
        Optional<LeaveRequest> originalLeaveRequest = leaveRequestRepository.findById(leaveRequestDTO.getId());
        if (originalLeaveRequest.isPresent()) {
            Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);//Search for the employee
            if (optionalEmployee.isPresent()) {
                Optional<LeaveCategory> optionalLeaveCategory = categoryRepository.findCategoryByTitle(leaveRequestDTO.getLeaveTitle());//Search for leave category that has been requested
                if (optionalLeaveCategory.isPresent()) {
                    Optional<LeaveBalance> foundBalanceOfEmployeeForOriginalRequest = optionalEmployee.get().getLeaveBalanceList()
                            .stream()
                            .filter(balance -> Objects.equals(balance.getCategory().getTitle(), originalLeaveRequest.get().getCategory().getTitle()))
                            .findAny(); //Search for the balance of the leave category for this employee for the original request
                    Optional<LeaveBalance> foundBalanceOfEmployeeForEditedRequest = optionalEmployee.get().getLeaveBalanceList()
                            .stream()
                            .filter(balance -> Objects.equals(balance.getCategory().getTitle(), leaveRequestDTO.getLeaveTitle()))
                            .findAny();//Search for the balance of the leave category for this employee for the edited request
                    if (foundBalanceOfEmployeeForEditedRequest.isPresent() && foundBalanceOfEmployeeForOriginalRequest.isPresent()) {
                        LeaveBalance employeesBalanceOriginal = foundBalanceOfEmployeeForOriginalRequest.get();
                        LeaveBalance employeesBalanceEdited = foundBalanceOfEmployeeForEditedRequest.get();
                        Date submitDate = leaveRequestDTO.getSubmitDate();
                        if (leaveRequestDTO.getEndDate().getTime() >= leaveRequestDTO.getStartDate().getTime()) {
                            System.out.println("New leave request edited:");
                            System.out.println("submit date:" + leaveRequestDTO.getSubmitDate());
                            System.out.println("start date:" + leaveRequestDTO.getStartDate());
                            System.out.println("end date:" + leaveRequestDTO.getEndDate());
                            System.out.println(leaveRequestDTO.getStartDate().getTime() >= leaveRequestDTO.getSubmitDate().getTime());
                            if (leaveRequestDTO.getStartDate().getTime() >= leaveRequestDTO.getSubmitDate().getTime()) {
                                employeesBalanceOriginal.addDaysTaken(-originalLeaveRequest.get().getDuration());
                                if (leaveRequestDTO.getDuration() <= employeesBalanceEdited.getDays() - employeesBalanceEdited.getDaysTaken()) {
                                    leaveRequestDTO.setStatus(Status.PENDING);
                                    leaveRequestDTO.setSubmitDate(submitDate);
                                    employeesBalanceEdited.setDaysTaken(employeesBalanceEdited.getDaysTaken() + leaveRequestDTO.getDuration());
                                    leaveBalanceRepository.save(employeesBalanceOriginal);
                                    leaveBalanceRepository.save(employeesBalanceEdited);
                                    LeaveRequest editLeaveRequest = new LeaveRequest(leaveRequestDTO, optionalEmployee.get(), optionalLeaveCategory.get());
                                    leaveRequestRepository.save(editLeaveRequest);
                                    System.out.println("saved leaveRequest with id:" + editLeaveRequest.getId());
                                    return leaveRequestDTO;
                                } else throw new ConflictException("Το υπόλοιπο ημερών δεν επαρκεί για την αίτηση άδειας αυτής της κατηγορίας (" + (employeesBalanceEdited.getDays() - employeesBalanceEdited.getDaysTaken()) + " ημέρες)");
                            } else throw new ConflictException("H έναρξη της άδειας πρέπει να προηγείται της σημερινής ημέρας");
                        } else throw new ConflictException("Η έναρξη της άδειας πρέπει να είναι πριν τη λήξη της.");
                    } else throw new BadDataException("Ο εργαζόμενος δεν δικαιούται άδειες αυτής της κατηγορίας.");
                } else throw new BadDataException("Δεν υπάρχει τέτοια κατηγορία άδειας");
            } else throw new NotFoundException("Δε βρέθηκε εργαζόμενους με το ζητούμενο id: " + employeeId);
        } else throw new NotFoundException("Δε βρέθηκε αίτημα με το ζητούμενο id: " + leaveRequestDTO.getId());
    }


    @Override
    public List<SubordinatesReqDTO> getSubordinatesReq(Integer supervisorId) {

        List<LeaveRequest> myLeaveRequestHistory = leaveRequestRepository.findLeaveRequestsForSupervisedEmployees(supervisorId);
        return myLeaveRequestHistory.stream()
                .map(leaveRequest -> new SubordinatesReqDTO(leaveRequest, leaveRequest.getEmployee()))
                .toList();
    }

    @Override
    public List<SubordinatesReqDTO> getAllSubordinatesReq(Integer supervisorId, List<EmployeeSupervisorDTO> subordinates) {
        List<SubordinatesReqDTO> subReqDTOs = new ArrayList<>();
        System.out.println(subordinates);
        for (EmployeeSupervisorDTO subordinate : subordinates) {
            subReqDTOs.addAll(leaveRequestRepository.leaveRequestHistoryOfEmployee(subordinate.getEmployeeId()).stream()
                    .map(leaveRequest -> new SubordinatesReqDTO(leaveRequest, leaveRequest.getEmployee()))
                    .toList());
        }
        return subReqDTOs;
    }
}





