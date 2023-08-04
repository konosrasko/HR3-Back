package com.open3hr.adeies.app.user.controller;

import com.open3hr.adeies.app.user.dto.EmployeeUserDTO;
import com.open3hr.adeies.app.user.dto.UserDTO;
import com.open3hr.adeies.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public UserDTO  getInfo(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserInfo(username);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public UserDTO findById(@PathVariable Integer id){
        return userService.findById(id);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('HR') OR hasRole('Admin')")
    public List<UserDTO> findAll(){
        return userService.findAll();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('Admin')")
    public List<EmployeeUserDTO> findUsersEmployeesForAdmin(){
        return userService.getEmployeeUserAdmin();
    }

    @GetMapping("/admin/{userId}")
    public EmployeeUserDTO findUsersEmployeesForAdminById(@PathVariable Integer userId){
        return userService.getEmployeeUserById(userId);
    }

    @PostMapping("/createAccount")
    @PreAuthorize("hasRole('Admin')")
    public UserDTO createAccount(@RequestBody UserDTO userDTO){
        userDTO.setId(0);
        return userService.createAccount(userDTO);
    }

    @PutMapping("/user/{userId}")
    @PreAuthorize("hasRole('Admin')")
    public UserDTO editUser(@RequestBody UserDTO userDTO, @PathVariable Integer userId){
        return userService.editUser(userDTO, userId);
    }

    @PutMapping("/{id}/changeStatus")
    @PreAuthorize("hasRole('Admin')")
    public UserDTO changeStatus(@PathVariable Integer id){
        return userService.updateStatus(id);
    }

    //TI EINAI AYTO
    @PutMapping("/{id}/supervisorRights")
    public UserDTO supervisorRights(@PathVariable Integer id){
        return userService.changeSupervisorRights(id);
    }

    @PutMapping("/{userId}/assignToEmployee/{employeeId}")
    @PreAuthorize("hasRole('Admin')")
    public UserDTO assignUserToEmployee(@PathVariable Integer userId, @PathVariable Integer employeeId){
        return userService.assignUserToEmployee(userId,employeeId);
    }

    @PutMapping("/{userId}/unassign")
    @PreAuthorize("hasRole('Admin')")
    public UserDTO unassignUserAccount(@PathVariable Integer userId){
        return userService.unassignUserAccount(userId);
    }
}