package com.open3hr.adeies.controllers;

import com.open3hr.adeies.dto.UserDTO;
import com.open3hr.adeies.repositories.UserRepository;
import com.open3hr.adeies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}" )
    public UserDTO findById(@PathVariable Integer id){
        return userService.findById(id);
    }

    @GetMapping("/users")
    public List<UserDTO> findAll(){
        return userService.findAll();
    }

    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable Integer id){
        userService.deleteById(id);
    }

    @PostMapping("/users/create")
    public UserDTO createAccount(@RequestBody UserDTO userDTO){
        userDTO.setId(0);
        return userService.createAccount(userDTO);
    }

    @PutMapping("/users/{id}/changeStatus")
    public UserDTO changeStatus(@PathVariable Integer id){
        return userService.updateStatus(id);
    }

    @PutMapping("/users/{id}/supervisorRights")
    public UserDTO supervisorRights(@PathVariable Integer id){
        return userService.changeSupervisorRights(id);
    }
}