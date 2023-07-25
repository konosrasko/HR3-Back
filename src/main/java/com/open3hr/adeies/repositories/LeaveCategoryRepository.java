package com.open3hr.adeies.repositories;

import com.open3hr.adeies.entities.LeaveCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveCategoryRepository extends JpaRepository<LeaveCategory, Integer> {
}
