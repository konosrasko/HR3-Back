package com.open3hr.adeies.services.impl;

import com.open3hr.adeies.dto.LeaveCategoryDTO;
import com.open3hr.adeies.entities.LeaveCategory;
import com.open3hr.adeies.repositories.LeaveCategoryRepository;
import com.open3hr.adeies.services.LeaveCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class LeaveCategoryServiceImpl implements LeaveCategoryService {
    @Autowired
    private LeaveCategoryRepository leaveCategoryRepository;

    @Override
    public List<LeaveCategoryDTO> findAll(){
        return leaveCategoryRepository.findAll().stream()
                .map(LeaveCategoryDTO::new)
                .toList();
    }

    @Override
    public LeaveCategoryDTO findById(Long Id){
        Optional<LeaveCategory> leaveCategory = leaveCategoryRepository.findById(Id);
        if(leaveCategory.isPresent()){
            return new LeaveCategoryDTO(leaveCategory.get());
        } else {
            throw new RuntimeException("Couldn't find leave category with id" + Id);
        }
    }

    @Override
    public LeaveCategoryDTO save(LeaveCategoryDTO leaveCategoryDTO){
        LeaveCategory leaveCategory = new LeaveCategory(leaveCategoryDTO);
        return new LeaveCategoryDTO(leaveCategoryRepository.save(leaveCategory));
    }
    @Override
    public void deleteById(Long Id) {
        this.leaveCategoryRepository.findById(Id).orElseThrow(()-> new RuntimeException("Leave category with id "+ Id +" not found"));
    }

}
