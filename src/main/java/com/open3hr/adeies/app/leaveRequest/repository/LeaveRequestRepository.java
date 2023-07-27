package com.open3hr.adeies.app.leaveRequest.repository;

import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
}
