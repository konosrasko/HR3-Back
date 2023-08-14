package com.open3hr.adeies.app.leaveCategory.controller;

import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;
import com.open3hr.adeies.app.leaveCategory.service.LeaveCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<LeaveCategoryDTO>> findAll(){
        return new ResponseEntity<>(leaveCategoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<List<LeaveCategoryDTO>> findAllActiveCategory(){
        return new ResponseEntity<>(leaveCategoryService.activeLeaveCategories(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') OR hasRole('HR') OR hasRole('Employee')")
    public ResponseEntity<LeaveCategoryDTO> findById(@PathVariable Integer id){
        return new ResponseEntity<>(leaveCategoryService.findById(id),HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<LeaveCategoryDTO> save(@RequestBody LeaveCategoryDTO leaveCategoryDTO){
        leaveCategoryDTO.setId(0);
        return new ResponseEntity<>(leaveCategoryService.createNewCategory(leaveCategoryDTO),HttpStatus.CREATED);
    }

    @PutMapping("")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<LeaveCategoryDTO> updateCategory(@RequestBody LeaveCategoryDTO leaveCategoryDTO){
        return new ResponseEntity<>(leaveCategoryService.editCategory(leaveCategoryDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity deleteById(@PathVariable Integer id){
        leaveCategoryService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}