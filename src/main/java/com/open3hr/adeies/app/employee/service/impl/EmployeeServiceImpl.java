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
import com.open3hr.adeies.app.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    public EmployeeDTO findEmployeeById(Integer id) {
        Optional<Employee> result = employeeRepository.findById(id);
        if (result.isPresent()) {
            return new EmployeeDTO(result.get());
        } else throw new RuntimeException("Couldn't find an employee with the id " + id);
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO);
        return new EmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    public void deleteById(Integer id) {
        this.employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee with id " + id + " not found"));
    }

    @Override
    public LeaveRequestDTO addLeaveRequest(LeaveRequestDTO leaveRequestDTO, int employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);//Search for the employee
        if(optionalEmployee.isPresent()){
            Optional<LeaveCategory> optionalLeaveCategory = categoryRepository.findCategoryByTitle(leaveRequestDTO.getLeaveTitle());//Search for leave category that has been requested
            if(optionalLeaveCategory.isPresent()){
                Optional<LeaveBalance> foundBalanceOfEmployee = optionalEmployee.get().getLeaveBalanceList()
                        .stream()
                        .filter(balance -> Objects.equals(balance.getCategory().getTitle(), leaveRequestDTO.getLeaveTitle()))
                        .findAny();//Search for the balance of the leave category for this employee
                if(foundBalanceOfEmployee.isPresent()){
                    LeaveBalance employeesBalance = foundBalanceOfEmployee.get();
                    Date submitDate = leaveRequestDTO.getSubmitDate();


                    if(leaveRequestDTO.getEndDate().getTime() >= leaveRequestDTO.getStartDate().getTime() ){
                        if(leaveRequestDTO.getStartDate().getTime() >= leaveRequestDTO.getSubmitDate().getTime()){

                            if(leaveRequestDTO.getDuration() <= employeesBalance.getDays() - employeesBalance.getDaysTaken()){
                                leaveRequestDTO.setId(0);
                                leaveRequestDTO.setStatus(Status.PENDING);
                                leaveRequestDTO.setSubmitDate(submitDate);
                                employeesBalance.setDaysTaken(employeesBalance.getDaysTaken() + leaveRequestDTO.getDuration());
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

    @Override
    public List<EmployeeDTO> employeesWithoutAccount() {
        List<Employee> employees = employeeRepository.findEmployeesWithoutUser();
        return employees.stream()
                .map(EmployeeDTO::new)
                .toList();
    }

    @Override
    public EmployeeDTO changeProfile(EmployeeDTO employeeDTO, Integer id) {
        Optional<Employee> result = employeeRepository.findById(id);
        if (result.isPresent()) {
            employeeRepository.save(new Employee(employeeDTO));
            return employeeDTO;
        } else {
            throw new RuntimeException("Employee not found!");
            // ### (id could be wrong) ###
        }
    }

    @Override
    public LeaveRequestDTO acceptLeaveRequest(Integer employeeId, Integer leaveRequestId) {
        Optional<Employee> myEmployee = employeeRepository.findById(employeeId);
        Optional<LeaveRequest> myLeaveRequest = leaveRequestRepository.findById(leaveRequestId);
        Optional<LeaveCategory> myLeaveCategory = categoryRepository.findById(myLeaveRequest.get().getCategory().getId());
        if (myLeaveRequest.isPresent()) {
            if (myEmployee.isPresent()) {
                myLeaveRequest.get().setStatus(Status.APPROVED);
                leaveRequestRepository.save(myLeaveRequest.get());
                myLeaveRequest.get().setCategory(myLeaveCategory.get());
                return new LeaveRequestDTO(myLeaveRequest.get(), myLeaveCategory.get());
            } else {
                throw new RuntimeException("Couldn't find employee!");
            }
        } else {
            throw new RuntimeException("Couldn't find leave request!");
        }
    }

    @Override
    public LeaveRequestDTO denyLeaveRequest(Integer employeeId, Integer leaveRequestId) {
        Optional<Employee> myEmployee = employeeRepository.findById(employeeId);
        Optional<LeaveRequest> myLeaveRequest = leaveRequestRepository.findById(leaveRequestId);
        Optional<LeaveCategory> myLeaveCategory = categoryRepository.findById(myLeaveRequest.get().getCategory().getId());
        if (myLeaveRequest.isPresent()) {
            if (myEmployee.isPresent()) {
                myLeaveRequest.get().setStatus(Status.DENIED);
                leaveRequestRepository.save(myLeaveRequest.get());
                myLeaveRequest.get().setCategory(myLeaveCategory.get());
                return new LeaveRequestDTO(myLeaveRequest.get(), myLeaveCategory.get());
            } else {
                throw new RuntimeException("Couldn't find employee!");
            }
        } else {
            throw new RuntimeException("Couldn't find leave request!");
        }
    }

    @Override
    public EmployeeDTO assignToSupervisor(Integer employeeId, Integer supervisorId) {
        Optional<Employee> myEmployee = employeeRepository.findById(employeeId);
        Optional<Employee> employee = employeeRepository.findById(supervisorId);
        if (myEmployee.isPresent()) {
            if (employee.isPresent()) {
                myEmployee.get().setSupervisorId(supervisorId);
                employeeRepository.save(myEmployee.get());
                return new EmployeeDTO(myEmployee.get());
            } else
                throw new RuntimeException("Couldn't find employee!");
        } else
            throw new RuntimeException("Couldn't find supervisor!");
    }

    @Override
    public EmployeeDTO unassignedToSupervisor(Integer employeeId, Integer supervisorId) {
        Optional<Employee> myEmployee = employeeRepository.findById(employeeId);
        if (myEmployee.isPresent()) {
            myEmployee.get().setSupervisorId(null);
            employeeRepository.save(myEmployee.get());
            return new EmployeeDTO(myEmployee.get());
        } else {
            throw new RuntimeException("Couldn't find employee!");
        }
    }

    @Override
    public List<LeaveRequestDTO> requestHistoryOfEmployee(Integer employeeId) {
        List<LeaveRequest> myLeaveRequestHistory = leaveRequestRepository.leaveRequestHistoryOfEmployee(employeeId);
        return myLeaveRequestHistory.stream()
                .map(leaveRequest -> new LeaveRequestDTO(leaveRequest, leaveRequest.getCategory()))
                .toList();
    }

    @Override
    public EmployeeDTO findEmployeeByUserName(String username) {
        Optional<User> myUser = userRepository.findUserByUsername(username);
        if(myUser.isPresent()){
            Optional<Employee> myEmployee = employeeRepository.findById(myUser.get().getEmployee().getId());
            if(myEmployee.isPresent()){
                return new EmployeeDTO(myEmployee.get());
            }else {
                throw new RuntimeException("Couldn't find employee");
            }
        }else {
            throw new RuntimeException("Couldn't find user");
        }
    }

    public boolean isSupervisor(int employeeId){
        boolean isSuper = false;
        Optional<Employee> foundEmployee = employeeRepository.findById(employeeId);
        if(foundEmployee.isPresent()){
            Optional<Employee> foundSuperV = employeeRepository.findIfSupervisor(employeeId);
            if(foundSuperV.isPresent()){
                isSuper = true;
            }
        }else throw new RuntimeException("No employee with this id found");
        return isSuper;
    }
}