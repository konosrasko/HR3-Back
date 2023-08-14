package com.open3hr.adeies.app.employee.service.impl;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.employee.dto.EmployeeSupervisorDTO;
import com.open3hr.adeies.app.employee.dto.miniEmployeeDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.employee.service.EmployeeService;
import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.exceptions.BadDataException;
import com.open3hr.adeies.app.exceptions.ConflictException;
import com.open3hr.adeies.app.exceptions.NotFoundException;
import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveBalance.repository.LeaveBalanceRepository;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.leaveRequest.repository.LeaveRequestRepository;
import com.open3hr.adeies.app.user.entity.User;
import com.open3hr.adeies.app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
    public List<EmployeeSupervisorDTO> findAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeSupervisorDTO> employeeSupervisorList = employeeList.stream()
                .map(employee -> {
                    if (employee.getSupervisorId() == null) {
                        employee.setSupervisorId(0); // Set supervisorId to 0 if null
                    }
                    String supervisorLastName = employeeRepository.findById(employee.getSupervisorId())
                            .map(Employee::getLastName)
                            .orElse(""); // Get supervisor's last name or set to empty string if not found
                    return new EmployeeSupervisorDTO(employee, supervisorLastName);
                })
                .collect(Collectors.toList());

            return employeeSupervisorList;
    }

    @Override
    public EmployeeDTO findEmployeeById(Integer id) {
        Optional<Employee> result = employeeRepository.findById(id);
        if (result.isPresent()) {
            return new EmployeeDTO(result.get());
        } else throw new NotFoundException("Could not find employee with given id of " + id);
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO);
        return new EmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    public void deleteById(Integer id) {
        this.employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Could not find employee with given id of " + id));
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
                        System.out.println("start date:" + leaveRequestDTO.getStartDate());
                        System.out.println("submit date:" + leaveRequestDTO.getSubmitDate());
                        System.out.println("start date >= end date:");
                        System.out.println(leaveRequestDTO.getStartDate().getTime() >= leaveRequestDTO.getSubmitDate().getTime() );
                        if(leaveRequestDTO.getStartDate().getTime() >= leaveRequestDTO.getSubmitDate().getTime() ){

                            if(leaveRequestDTO.getDuration() <= employeesBalance.getDays() - employeesBalance.getDaysTaken()){
                                leaveRequestDTO.setId(0);
                                leaveRequestDTO.setStatus(Status.PENDING);
                                leaveRequestDTO.setSubmitDate(submitDate);
                                employeesBalance.setDaysTaken(employeesBalance.getDaysTaken() + leaveRequestDTO.getDuration());
                                LeaveRequest leaveRequest = new LeaveRequest(leaveRequestDTO, optionalEmployee.get(), optionalLeaveCategory.get());
                                balanceRepository.save(employeesBalance);
                                return new LeaveRequestDTO(leaveRequestRepository.save(leaveRequest), optionalLeaveCategory.get());

                            }else throw new ConflictException("The employee does not have as many leave days as requested");
                        }else throw new ConflictException("Start's date can't be before submitDate's date");
                    }else throw new ConflictException("End's date can't be before start's date");
                }else throw new BadDataException("This employee doesn't have this type of leave category");
            }else throw new BadDataException("There is no such leave category");
        }else throw new NotFoundException("Could not find employee with given id of " + employeeId);
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
            throw new NotFoundException("Could not find employee with given id of " + id);
        }
    }

    @Override
    public EmployeeDTO assignToSupervisor(Integer employeeId, Integer supervisorId) {
        Optional<Employee> myEmployee = employeeRepository.findById(employeeId);
        Optional<Employee> employee = employeeRepository.findById(supervisorId);
        //TODO: check if supervisor has is_supervisor=true !
        if (myEmployee.isPresent()) {
            if (employee.isPresent()) {
                myEmployee.get().setSupervisorId(supervisorId);
                employeeRepository.save(myEmployee.get());
                return new EmployeeDTO(myEmployee.get());
            } else
                throw new NotFoundException("Could not find employee with given id of " + employeeId);
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
            throw new NotFoundException("Could not find employee with given id of " + employeeId);
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
                throw new NotFoundException("Could not find employee");
            }
        }else {
            throw new NotFoundException("Could not find user" + username);
        }
    }

    @Override
    public LeaveRequestDTO approveLeaveRequest(Integer leaveReqId) {
        Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findById(leaveReqId);
        if (leaveRequest.isPresent()){
            leaveRequest.get().setStatus(Status.APPROVED);
            return  new LeaveRequestDTO(leaveRequestRepository.save(leaveRequest.get()));
        }
        throw new NotFoundException("Could not find leave request with id: " + leaveReqId);
    }

    @Override
    public LeaveRequestDTO declineLeaveRequest(Integer leaveReqId) {
        Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findById(leaveReqId);
        if (leaveRequest.isPresent()){
            leaveRequest.get().setStatus(Status.DENIED);
            return  new LeaveRequestDTO(leaveRequestRepository.save(leaveRequest.get()));
        }
        throw new NotFoundException("Could not find leave request with id: " + leaveReqId);
    }

    @Override
    public List<miniEmployeeDTO> findAllSupervisors() {

       List<Employee> supervisors = employeeRepository.findAllSupervisors();
        System.out.println(supervisors);
        return supervisors.stream().map(supervisor ->
            new miniEmployeeDTO(supervisor)).toList();


    }

    public boolean isSupervisor(int employeeId){
        boolean isSuper = false;
        Optional<Employee> foundEmployee = employeeRepository.findById(employeeId);
        if(foundEmployee.isPresent()){
            Optional<Employee> foundSuperV = employeeRepository.findIfSupervisor(employeeId);
            if(foundSuperV.isPresent()){
                isSuper = true;
            }
        }else throw new NotFoundException("Could not find employee with given id of " + employeeId);
        return isSuper;
    }
}