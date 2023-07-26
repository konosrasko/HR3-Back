package com.open3hr.adeies.repositories;

import com.open3hr.adeies.entities.LeaveCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveCategoryRepository extends JpaRepository<LeaveCategory, Integer> {
    @Query("SELECT lc FROM LeaveCategory lc WHERE lc.title = ?1")
    Optional<LeaveCategory> findCategoryByTitle(String title);

}
