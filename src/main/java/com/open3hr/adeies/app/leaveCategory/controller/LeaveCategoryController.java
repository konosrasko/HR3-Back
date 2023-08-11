package com.open3hr.adeies.app.leaveCategory.controller;

import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;
import com.open3hr.adeies.app.leaveCategory.service.LeaveCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leavecategory")
public class LeaveCategoryController {

    @Autowired
    private LeaveCategoryService leaveCategoryService;

    @GetMapping("")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public List<LeaveCategoryDTO> findAll(){
        return leaveCategoryService.findAll();
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public List<LeaveCategoryDTO> findAllActiveCategory(){
        return leaveCategoryService.activeLeaveCategories();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public LeaveCategoryDTO findById(@PathVariable Integer id){
        return leaveCategoryService.findById(id);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('HR')")
    public LeaveCategoryDTO save(@RequestBody LeaveCategoryDTO leaveCategoryDTO){
        leaveCategoryDTO.setId(0);
        return leaveCategoryService.createNewCategory(leaveCategoryDTO);
    }

    @PutMapping("")
    @PreAuthorize("hasRole('HR')")
    public LeaveCategoryDTO updateCategory(@RequestBody LeaveCategoryDTO leaveCategoryDTO){
        return leaveCategoryService.editCategory(leaveCategoryDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HR')")
    public void deleteById(@PathVariable Integer id){
        leaveCategoryService.deleteById(id);
    }

}