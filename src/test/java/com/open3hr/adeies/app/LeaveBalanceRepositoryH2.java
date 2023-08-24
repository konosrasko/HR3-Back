package com.open3hr.adeies.app;

import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveBalanceRepositoryH2  extends JpaRepository<LeaveBalance, Integer> {
}

