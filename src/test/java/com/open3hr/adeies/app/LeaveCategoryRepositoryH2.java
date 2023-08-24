package com.open3hr.adeies.app;

import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveCategoryRepositoryH2 extends JpaRepository<LeaveCategory, Integer> {
    @Query(value = "SELECT * FROM leave_category WHERE title = :title",nativeQuery = true)
    Optional<LeaveCategory> findCategoryByTitle(String title);

}
