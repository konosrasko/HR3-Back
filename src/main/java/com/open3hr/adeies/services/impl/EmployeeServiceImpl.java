package com.open3hr.adeies.services.impl;

import com.open3hr.adeies.dto.EmployeeDTO;
import com.open3hr.adeies.entities.Employee;
import com.open3hr.adeies.repositories.EmployeeRepository;
import com.open3hr.adeies.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return employeeRepository.findAll().stream().map(EmployeeDTO::new).collect(Collectors.toList());
    }
    @Override
    public EmployeeDTO findEmployeeById(Long id){
        Optional<Employee> result = employeeRepository.findById(id);
        if (result.isPresent()) {
            return new EmployeeDTO(result.get());
        }
        throw new RuntimeException("Dead enas");
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        return new EmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    public void deleteById(Long id) {
        this.employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee with id "+ id +" not found"));
    }


}
