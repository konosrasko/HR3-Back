package com.open3hr.adeies.services;

import com.open3hr.adeies.dto.LeaveCategoryDTO;

import java.util.List;

public interface LeaveCategoryService {
    List<LeaveCategoryDTO> findAll();

    LeaveCategoryDTO findById(Long Id);

    LeaveCategoryDTO save(LeaveCategoryDTO leaveCategoryDTO);

    void deleteById(Long Id);


}
