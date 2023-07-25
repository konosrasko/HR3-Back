package com.open3hr.adeies.controllers;

import com.open3hr.adeies.dto.LeaveCategoryDTO;
import com.open3hr.adeies.services.LeaveCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaveCategoryController {
    @Autowired
    private LeaveCategoryService leaveCategoryService;
    @GetMapping("/leavecategory")
    public List<LeaveCategoryDTO> findAll(){
        return  leaveCategoryService.findAll();
    }

    @GetMapping("/leavecategory/{id}")
    public LeaveCategoryDTO findById(@PathVariable Long id){
        return leaveCategoryService.findById(id);
    }

    @PostMapping("/leavecategory")
    public LeaveCategoryDTO save(@RequestBody LeaveCategoryDTO leaveCategoryDTO){
        leaveCategoryDTO.setId(0L);
        return leaveCategoryService.save(leaveCategoryDTO);
    }

    @PutMapping("/leavecategory")
    public LeaveCategoryDTO update(@RequestBody LeaveCategoryDTO leaveCategoryDTO){
        return leaveCategoryService.save(leaveCategoryDTO);
    }

    @DeleteMapping("/leavecategory/{id}")
    public void deleteById(@PathVariable Long id){
        leaveCategoryService.deleteById(id);
    }

}
