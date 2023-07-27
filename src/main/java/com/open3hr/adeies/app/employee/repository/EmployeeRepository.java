package com.open3hr.adeies.app.employee.repository;

import com.open3hr.adeies.app.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query (value="SELECT e.* FROM employee e LEFT JOIN user u ON e.id = u.employee_id WHERE u.employee_id IS NULL;", nativeQuery = true)
    List<Employee> findEmployeesWithoutUser();
}
