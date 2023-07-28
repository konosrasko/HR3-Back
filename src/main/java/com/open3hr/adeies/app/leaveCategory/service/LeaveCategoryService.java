package com.open3hr.adeies.app.leaveCategory.service;

import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;

import java.util.List;

public interface LeaveCategoryService {
    List<LeaveCategoryDTO> findAll();

    LeaveCategoryDTO findById(Integer Id);

    LeaveCategoryDTO createNewCategory(LeaveCategoryDTO leaveCategoryDTO);

    void deleteById(Integer Id);

}