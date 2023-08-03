package com.open3hr.adeies.app.user.controller;

import com.open3hr.adeies.app.user.dto.UserDTO;
import com.open3hr.adeies.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public String  getInfo(){
//        String username= SecurityContextHolder.getContext().getAuthentication().getName();
//        return userService.getUserInfo(username);
        return "TA KATAFERAMEEEEEEEEE";
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Integer id){
        return userService.findById(id);
    }

    @GetMapping("")
    public List<UserDTO> findAll(){
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        userService.deleteById(id);
    }

    @PostMapping("/createAccount")
    public UserDTO createAccount(@RequestBody UserDTO userDTO){
        userDTO.setId(0);
        return userService.createAccount(userDTO);
    }

    @PutMapping("/{id}/changeStatus")
    public UserDTO changeStatus(@PathVariable Integer id){
        return userService.updateStatus(id);
    }

    //TI EINAI AYTO
    @PutMapping("/{id}/supervisorRights")
    public UserDTO supervisorRights(@PathVariable Integer id){
        return userService.changeSupervisorRights(id);
    }

    @PutMapping("/{userId}/assignToEmployee/{employeeId}")
    public UserDTO assignUserToEmployee(@PathVariable Integer userId, @PathVariable Integer employeeId){
        return userService.assignUserToEmployee(userId,employeeId);
    }

    @PutMapping("/{userId}/unassign")
    public UserDTO unassignUserAccount(@PathVariable Integer userId){
        return userService.unassignUserAccount(userId);
    }
}