package com.open3hr.adeies.app.employee.repository;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.employee.dto.miniEmployeeDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query (value="SELECT e.* FROM employee e LEFT JOIN user u ON e.id = u.employee_id WHERE u.employee_id IS NULL;", nativeQuery = true)
    List<Employee> findEmployeesWithoutUser();

    @Query (value="SELECT e.* FROM employee e WHERE e.supervisor_id IS NOT NULL;", nativeQuery = true)
    List<Employee> supervisorEmployees();

    @Query (value="SELECT e.* FROM employee e WHERE e.supervisor_id = :employeeId;", nativeQuery = true)
    Optional<Employee> findIfSupervisor(int employeeId);

    @Query(value = "SELECT employee.* FROM employee  INNER JOIN user  ON employee.id = user.employee_id   WHERE user.is_supervisor = 1;", nativeQuery = true)
    List<Employee>findAllSupervisors();
}