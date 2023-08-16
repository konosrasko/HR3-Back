package com.open3hr.adeies.app.leaveCategory.service.impl;

import com.open3hr.adeies.app.exceptions.ConflictException;
import com.open3hr.adeies.app.exceptions.NotFoundException;
import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveCategory.service.LeaveCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    @Override
    public List<LeaveCategoryDTO> activeLeaveCategories(){
        return leaveCategoryRepository.findAll().stream()
                .filter(LeaveCategory::isActive)
                .map(LeaveCategoryDTO::new)
                .toList();
    }

    @Override
    public LeaveCategoryDTO findById(Integer categoryId) {
        Optional<LeaveCategory> leaveCategory = leaveCategoryRepository.findById(categoryId);
        if (leaveCategory.isPresent()) {
            return new LeaveCategoryDTO(leaveCategory.get());
        } else {
            throw new NotFoundException("Η κατηγορία Άδειας με το id :" + categoryId + "δεν βρέθηκε");
        }
    }

    @Override
    public LeaveCategoryDTO createNewCategory(LeaveCategoryDTO leaveCategoryDTO) {
        Optional<LeaveCategory> foundCategory = leaveCategoryRepository.findCategoryByTitle(leaveCategoryDTO.getTitle());
        if(foundCategory.isPresent()){
            throw new ConflictException("Υπάρχει ήδη μία κατηγορία άδειας με αυτόν τον τίτλο: " + leaveCategoryDTO.getTitle());
        }else{
            LeaveCategory leaveCategory = new LeaveCategory(leaveCategoryDTO);
            return new LeaveCategoryDTO(leaveCategoryRepository.save(leaveCategory));
        }
    }

    @Override
    public LeaveCategoryDTO editCategory(LeaveCategoryDTO leaveCategoryDTO){
        if(leaveCategoryRepository.existsById(leaveCategoryDTO.getId())){
            List<LeaveCategory> allCategories = leaveCategoryRepository.findAll();
            for(LeaveCategory category : allCategories){
                if(!Objects.equals(category.getId(), leaveCategoryDTO.getId()) && Objects.equals(category.getTitle(), leaveCategoryDTO.getTitle())){
                    throw new ConflictException("Υπάρχει ήδη μία κατηγορία άδειας με αυτόν τον τίτλο: " + leaveCategoryDTO.getTitle());
                }
            }
            return new LeaveCategoryDTO(leaveCategoryRepository.save(new LeaveCategory(leaveCategoryDTO)));
        }else throw new NotFoundException("Αυτή η κατηγορία άδειας δεν υπάρχει ");
    }

    @Override
    public void deleteById(Integer categoryId) {
        Optional<LeaveCategory> result = leaveCategoryRepository.findById(Math.toIntExact(categoryId));
        if (result.isPresent()) {
            leaveCategoryRepository.deleteById(categoryId);
        } else {
            throw new NotFoundException("Η κατηγορία άδειας με το id : " + categoryId + "δεν βρέθηκε");
        }
    }
}