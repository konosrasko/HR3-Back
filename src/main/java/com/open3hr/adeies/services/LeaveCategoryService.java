package com.open3hr.adeies.services;

import com.open3hr.adeies.dto.LeaveCategoryDTO;

import java.util.List;

public interface LeaveCategoryService {
    List<LeaveCategoryDTO> findAll();

    LeaveCategoryDTO findById(Integer Id);

    LeaveCategoryDTO save(LeaveCategoryDTO leaveCategoryDTO);

    void deleteById(Integer Id);


}
