package com.open3hr.adeies.app.leaveCategory.service.impl;

import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveCategory.service.LeaveCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveCategoryServiceImpl implements LeaveCategoryService {
    @Autowired
    private LeaveCategoryRepository leaveCategoryRepository;

    @Override

    public List<LeaveCategoryDTO> findAll() {
        return leaveCategoryRepository.findAll().stream()
                .map(LeaveCategoryDTO::new)
                .toList();
    }

    //ΕΔΩ ΝΑ ΣΟΥ ΔΙΝΕΙ ΜΟΝΟ ΤΑ ΑΚΤΙΒ
    //ΔΙΚΟ ΣΑΣ
    /*
    */

    @Override
    public LeaveCategoryDTO findById(Integer Id) {
        Optional<LeaveCategory> leaveCategory = leaveCategoryRepository.findById(Id);
        if (leaveCategory.isPresent()) {
            return new LeaveCategoryDTO(leaveCategory.get());
        } else {
            throw new RuntimeException("Couldn't find leave category with id" + Id);
        }
    }

    @Override
    public LeaveCategoryDTO createNewCategory(LeaveCategoryDTO leaveCategoryDTO) {
        Optional<LeaveCategory> foundCategory = leaveCategoryRepository.findCategoryByTitle(leaveCategoryDTO.getTitle());
        if(foundCategory.isPresent()){
            throw new RuntimeException("There is already a leave category with the title: " + leaveCategoryDTO.getTitle());
        }else{
            LeaveCategory leaveCategory = new LeaveCategory(leaveCategoryDTO);
            return new LeaveCategoryDTO(leaveCategoryRepository.save(leaveCategory));
        }
    }

    @Override
    public void deleteById(Integer id) {
        Optional<LeaveCategory> result = leaveCategoryRepository.findById(Math.toIntExact(id));
        if (result.isPresent()) {
            leaveCategoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Did not find leave category id- " + id);
        }
    }
}