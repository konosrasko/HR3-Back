package com.open3hr.adeies.app.leaveBalance.service.impl;

import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.exceptions.NotFoundException;
import com.open3hr.adeies.app.leaveBalance.dto.LeaveBalanceDTO;
import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveBalance.repository.LeaveBalanceRepository;
import com.open3hr.adeies.app.leaveBalance.service.LeaveBalanceService;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService {
    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private LeaveCategoryRepository categoryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<LeaveBalanceDTO> findAll() {
        return leaveBalanceRepository.findAll().stream()
                .map(leaveBalance -> {
                    Optional<LeaveCategory> categoryOfBalance = categoryRepository.findById(leaveBalance.getCategory().getId());
                    if(categoryOfBalance.isPresent()){
                        return new LeaveBalanceDTO(leaveBalance, categoryOfBalance.get());
                    }else throw new NotFoundException("Σφάλμα με την κατηγορία αδειών");
                })
                .toList();
    }

    @Override
    public LeaveBalanceDTO findById(Integer id) {
        Optional<LeaveBalance> myLeaveBalance = leaveBalanceRepository.findById(id);
        if(myLeaveBalance.isPresent()){
            Optional<LeaveCategory> category = categoryRepository.findById(myLeaveBalance.get().getCategory().getId());
            if(category.isPresent()){
                return new LeaveBalanceDTO(myLeaveBalance.get(), category.get());
            } else throw new NotFoundException("Υπήρξε σφάλμα με το κλειδί της κατηγορίας");
        } else throw new NotFoundException("Δεν βρέθηκε υπόλοιπο άδειας με id: "+ id);
    }

    @Override
    public void addLeaveBalanceToEmployee(LeaveBalanceDTO leaveBalanceDTO, Integer employeeId) {
        leaveBalanceDTO.setId(0);
        Optional<Employee> foundEmployee = employeeRepository.findById(employeeId);
        if(foundEmployee.isPresent()){
            Optional<LeaveCategory> foundCategory = categoryRepository.findCategoryByTitle(leaveBalanceDTO.getCategoryTitle());
            if(foundCategory.isPresent()){
                LeaveBalance myLeaveBalance = new LeaveBalance(leaveBalanceDTO, foundEmployee.get(), foundCategory.get());
                leaveBalanceRepository.save(myLeaveBalance);
            }else throw new NotFoundException("Αυτός ο τίτλος κατηγορίας δεν υπάρχει!");
        } else throw new NotFoundException("Δεν βρέθηκε ο εργαζόμενους για να του περαστεί νέα κατηγορία άδειας");
    }

    @Override
    public List<LeaveBalanceDTO> showBalancesOfEmployee(int employeeId){
        Optional<Employee> foundEmployee = employeeRepository.findById(employeeId);
        if(foundEmployee.isPresent()){
            return foundEmployee.get().getLeaveBalanceList()
                    .stream()
                    .map(leaveBalance -> {
                        Optional<LeaveCategory> categoryOfBalance = categoryRepository.findById(leaveBalance.getCategory().getId());
                        if(categoryOfBalance.isPresent()){
                            return new LeaveBalanceDTO(leaveBalance, categoryOfBalance.get());
                        }else throw new RuntimeException("Πρόβλημα με το id τής Κατηγορίας Άδειας");
                    })
                    .toList();
        }else throw new NotFoundException("Ο εργαζόμενος με το συγκεκριμένο id δεν βρέθηκε");
    }

    @Override
    public void editLeaveBalanceOfEmployee(LeaveBalanceDTO balanceDTO, Integer employeeId){
        Optional<Employee> foundEmployee = employeeRepository.findById(employeeId);
        if(foundEmployee.isPresent()){
            Optional<LeaveCategory> foundCategory = categoryRepository.findCategoryByTitle(balanceDTO.getCategoryTitle());
            if(foundCategory.isPresent()){
                LeaveBalance editedLeaveBalance = new LeaveBalance(balanceDTO, foundEmployee.get(), foundCategory.get());
                leaveBalanceRepository.save(editedLeaveBalance);
            }else throw new NotFoundException("Η κατηορία άδειας δεν βρέθηκε για επεξεργασία");
        }else throw new NotFoundException("Ο εργαζόμενος δεν βρέθηκε");
    }

    @Override
    public void deleteLeaveBalanceOfEmployee(int employeeId, int leaveBalanceId){
        if(employeeRepository.existsById(employeeId)){
            List<LeaveBalance> allBalancesOfEmployee = leaveBalanceRepository.findAll()
                    .stream()
                    .filter(leaveBalance -> leaveBalance.getEmployee().getId() == employeeId)
                    .toList();
            Optional<LeaveBalance> leaveToDelete = allBalancesOfEmployee
                    .stream()
                    .filter(leaveBalance -> leaveBalance.getId() == leaveBalanceId)
                    .findFirst();
            if(leaveToDelete.isPresent()){
                leaveBalanceRepository.deleteById(leaveToDelete.get().getId());
            }else throw new NotFoundException("Η ζητούμενη άδεια δεν βρέθηκε για διαγραφή");
        }else throw new NotFoundException("Ο ζητούμενος εργαζόμενος δεν βρέθηκε");
    }
}
