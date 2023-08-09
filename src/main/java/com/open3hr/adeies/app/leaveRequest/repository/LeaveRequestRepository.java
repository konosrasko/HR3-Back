package com.open3hr.adeies.app.leaveRequest.repository;

import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
    @Query(value = "SELECT * FROM leave_request WHERE employee_id=:employeeId",nativeQuery = true)
    List<LeaveRequest> leaveRequestHistoryOfEmployee(@Param("employeeId") Integer employee_id);



    @Query(value = "SELECT lr.* FROM leave_request lr JOIN employee ON employee_id = lr.employee.id WHERE employee.supervisor_id=:supervisorId", nativeQuery = true)
    List<LeaveRequest> findLeaveRequestsForSupervisedEmployees(@Param("supervisorId") Integer supervisor_Id);

}
