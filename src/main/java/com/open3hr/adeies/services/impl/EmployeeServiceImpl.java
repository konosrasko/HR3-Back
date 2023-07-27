package com.open3hr.adeies.services.impl;

import com.open3hr.adeies.dto.EmployeeDTO;
import com.open3hr.adeies.dto.LeaveRequestDTO;
import com.open3hr.adeies.entities.Employee;
import com.open3hr.adeies.entities.LeaveCategory;
import com.open3hr.adeies.entities.LeaveRequest;
import com.open3hr.adeies.repositories.EmployeeRepository;
import com.open3hr.adeies.repositories.LeaveCategoryRepository;
import com.open3hr.adeies.repositories.LeaveRequestRepository;
import com.open3hr.adeies.services.EmployeeService;
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
            Optional<LeaveCategory> optionalLeaveCategory = categoryRepository.findCategoryByTitle(leaveRequestDTO.getLeaveTitle());
            if(optionalLeaveCategory.isPresent()){

                LeaveRequest leaveRequest= new LeaveRequest(leaveRequestDTO, optionalEmployee.get(), optionalLeaveCategory.get());
                return new LeaveRequestDTO(leaveRequestRepository.save(leaveRequest), optionalLeaveCategory.get());

            }else throw new RuntimeException("There is no such leave category");
        }else throw new RuntimeException("There is no employee with this id");
    }
}
