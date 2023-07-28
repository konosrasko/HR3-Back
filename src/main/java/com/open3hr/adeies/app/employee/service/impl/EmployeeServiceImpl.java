package com.open3hr.adeies.app.employee.service.impl;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.employee.service.EmployeeService;
import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveBalance.repository.LeaveBalanceRepository;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.leaveRequest.repository.LeaveRequestRepository;
import com.open3hr.adeies.app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private LeaveBalanceRepository balanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveCategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO findEmployeeById(Integer id){
        Optional<Employee> result = employeeRepository.findById(id);
        if (result.isPresent()) {
            return new EmployeeDTO(result.get());
        }else throw new RuntimeException("Couldn't find an employee with the id "+ id);
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO);
        return new EmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    public void deleteById(Integer id) {
        this.employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee with id "+ id +" not found"));
    }

    @Override
    public LeaveRequestDTO addLeaveRequest(LeaveRequestDTO leaveRequestDTO, int employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isPresent()){
            Employee selectedEmployee = optionalEmployee.get();
            Optional<LeaveCategory> optionalLeaveCategory = categoryRepository.findCategoryByTitle(leaveRequestDTO.getLeaveTitle());
            if(optionalLeaveCategory.isPresent()){
                Optional<LeaveBalance> foundBalanceOfEmployee = selectedEmployee.getLeaveBalanceList()
                        .stream()
                        .filter(balance -> Objects.equals(balance.getCategory().getTitle(), leaveRequestDTO.getLeaveTitle()))
                        .findAny();
                if(foundBalanceOfEmployee.isPresent()){
                    LeaveBalance employeesBalance = foundBalanceOfEmployee.get();
                    Date submitDate = new Date();
                    long todayStartDiff;
                    long startDate = leaveRequestDTO.getStartDate().getTime();
                    long endDate = leaveRequestDTO.getEndDate().getTime();
                    long toDate = submitDate.getTime();

                    if(toDate > startDate){
                        todayStartDiff = startDate - toDate;
                    } else {
                        todayStartDiff = Math.abs(toDate - startDate);
                    }

                    long todayStartDiffToDays = TimeUnit.DAYS.convert(todayStartDiff, TimeUnit.MILLISECONDS);

                    if(endDate >= startDate){
                        if(todayStartDiffToDays >= 0){

                            long startEndDiff = Math.abs(endDate - startDate);
                            long startEndDiffToDays = TimeUnit.DAYS.convert(startEndDiff, TimeUnit.MILLISECONDS) + 1;

                            if(startEndDiffToDays <= employeesBalance.getDays()){
                                leaveRequestDTO.setId(0);
                                leaveRequestDTO.setStatus(Status.PENDING);
                                leaveRequestDTO.setSubmitDate(submitDate);
                                leaveRequestDTO.setDuration((int) startEndDiffToDays);
                                employeesBalance.setDays(employeesBalance.getDays() - (int) startEndDiffToDays);
                                employeesBalance.setDaysTaken(employeesBalance.getDaysTaken() + (int) startEndDiffToDays);
                                LeaveRequest leaveRequest = new LeaveRequest(leaveRequestDTO, optionalEmployee.get(), optionalLeaveCategory.get());
                                balanceRepository.save(employeesBalance);
                                return new LeaveRequestDTO(leaveRequestRepository.save(leaveRequest), optionalLeaveCategory.get());

                            }else throw new RuntimeException("The employee does not have as many leave days as requested");
                        }else throw new RuntimeException("Start's date can't be before submitDate's date");
                    }else throw new RuntimeException("End's date can't be before start's date");
                }else throw new RuntimeException("This employee doesn't have this type of leave category");
            }else throw new RuntimeException("There is no such leave category");
        }else throw new RuntimeException("There is no employee with this id");
    }

    public List<EmployeeDTO> employeesWithoutAccount() {
        List<Employee> employees = employeeRepository.findEmployeesWithoutUser();
        return employees.stream()
                .map(EmployeeDTO::new)
                .toList();
    }
}