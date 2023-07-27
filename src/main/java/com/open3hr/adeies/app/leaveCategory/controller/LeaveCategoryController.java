package com.open3hr.adeies.app.leaveCategory.controller;

import com.open3hr.adeies.app.leaveCategory.dto.LeaveCategoryDTO;
import com.open3hr.adeies.app.leaveCategory.service.LeaveCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leavecategory")
public class LeaveCategoryController {
    @Autowired
    private LeaveCategoryService leaveCategoryService;

    @GetMapping("/")
    public List<LeaveCategoryDTO> findAll(){
        return  leaveCategoryService.findAll();
    }

    @GetMapping("/{id}")
    public LeaveCategoryDTO findById(@PathVariable Integer id){
        return leaveCategoryService.findById(id);
    }

    @PostMapping("/")
    public LeaveCategoryDTO save(@RequestBody LeaveCategoryDTO leaveCategoryDTO){
        leaveCategoryDTO.setId(0);
        return leaveCategoryService.createNewCategory(leaveCategoryDTO);
    }

    @PutMapping("/")
    public LeaveCategoryDTO update(@RequestBody LeaveCategoryDTO leaveCategoryDTO){
        return leaveCategoryService.createNewCategory(leaveCategoryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        leaveCategoryService.deleteById(id);
    }

}
