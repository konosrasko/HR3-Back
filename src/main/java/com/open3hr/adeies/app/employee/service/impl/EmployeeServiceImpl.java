package com.open3hr.adeies.app.employee.service.impl;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.employee.service.EmployeeService;
import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.leaveRequest.repository.LeaveRequestRepository;
import com.open3hr.adeies.app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

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
    public LeaveRequestDTO addLeaveRequest(LeaveRequestDTO leaveRequestDTO, Integer employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isPresent()){
            Optional<LeaveCategory> optionalLeaveCategory = categoryRepository.findCategoryByTitle(leaveRequestDTO.getLeaveTitle());
            if(optionalLeaveCategory.isPresent()){
                LeaveRequest leaveRequest= new LeaveRequest(leaveRequestDTO, optionalEmployee.get(), optionalLeaveCategory.get());
                return new LeaveRequestDTO(leaveRequestRepository.save(leaveRequest), optionalLeaveCategory.get());
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
        if (result.isPresent()){
            employeeRepository.save(new Employee(employeeDTO));
            return employeeDTO;
        }else {
            throw new RuntimeException("Employee not found!");
            // ### (id could be wrong) ###
        }
    }

    @Override
    public LeaveRequestDTO acceptLeaveRequest(Integer employeeId, Integer leaveRequestId){
        Optional<Employee> myEmployee = employeeRepository.findById(employeeId);
        Optional<LeaveRequest> myLeaveRequest = leaveRequestRepository.findById(leaveRequestId);
        Optional<LeaveCategory> myLeaveCategory  = categoryRepository.findById(myLeaveRequest.get().getCategory().getId());
        if(myLeaveRequest.isPresent()){
            if(myEmployee.isPresent()){
                myLeaveRequest.get().setStatus(Status.APPROVED);
                leaveRequestRepository.save(myLeaveRequest.get());
                myLeaveRequest.get().setCategory(myLeaveCategory.get());
                return new LeaveRequestDTO(myLeaveRequest.get(), myLeaveCategory.get());
            }else {
                throw new RuntimeException("Couldn't find employee!");
            }
        }else{
            throw new RuntimeException("Couldn't find leave request!");
        }
    }

    @Override
    public LeaveRequestDTO denyLeaveRequest(Integer employeeId, Integer leaveRequestId){
        Optional<Employee> myEmployee = employeeRepository.findById(employeeId);
        Optional<LeaveRequest> myLeaveRequest = leaveRequestRepository.findById(leaveRequestId);
        Optional<LeaveCategory> myLeaveCategory  = categoryRepository.findById(myLeaveRequest.get().getCategory().getId());
        if(myLeaveRequest.isPresent()){
            if(myEmployee.isPresent()){
                myLeaveRequest.get().setStatus(Status.DENIED);
                leaveRequestRepository.save(myLeaveRequest.get());
                myLeaveRequest.get().setCategory(myLeaveCategory.get());
                return new LeaveRequestDTO(myLeaveRequest.get(), myLeaveCategory.get());
            }else {
                throw new RuntimeException("Couldn't find employee!");
            }
        }else{
            throw new RuntimeException("Couldn't find leave request!");
        }
    }


}
