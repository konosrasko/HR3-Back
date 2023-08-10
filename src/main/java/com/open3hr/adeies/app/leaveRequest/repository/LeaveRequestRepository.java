package com.open3hr.adeies.app.leaveRequest.repository;

import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
    @Query(value = "SELECT * FROM leave_request WHERE employee_id=:employeeId",nativeQuery = true)
    List<LeaveRequest> leaveRequestHistoryOfEmployee(@Param("employeeId") Integer employee_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM leave_request WHERE id = :id", nativeQuery = true)
    void deleteByIdNative(@Param("id") Integer id);

    @Query(value = "SELECT lr.*,e.first_name FROM leave_request lr JOIN employee e ON e.id = lr.employee_id WHERE e.supervisor_id=:supervisorId", nativeQuery = true)
    List<LeaveRequest> findLeaveRequestsForSupervisedEmployees(@Param("supervisorId") Integer supervisor_Id);

}
