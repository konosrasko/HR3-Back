package com.open3hr.adeies.app.leaveCategory.service;

import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;

import java.util.List;

public interface LeaveCategoryService {
    List<LeaveCategoryDTO> findAll();

    List<LeaveCategoryDTO> activeLeaveCategories();

    LeaveCategoryDTO findById(Integer categoryId);

    LeaveCategoryDTO createNewCategory(LeaveCategoryDTO leaveCategoryDTO);

    void deleteById(Integer categoryId);

    LeaveCategoryDTO editCategory(LeaveCategoryDTO leaveCategoryDTO);

}