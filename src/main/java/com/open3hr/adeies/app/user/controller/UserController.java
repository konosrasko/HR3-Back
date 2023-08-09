package com.open3hr.adeies.app.user.controller;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.user.dto.EmployeeUserDTO;
import com.open3hr.adeies.app.user.dto.RolesDTO;
import com.open3hr.adeies.app.user.dto.UserDTO;
import com.open3hr.adeies.app.user.entity.User;
import com.open3hr.adeies.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //used in: http://localhost:4200/home/landing
    @GetMapping("/roles")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public RolesDTO getMyRoles(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        int logged_id = userService.getUserInfo(username).getId();
        return userService.getUserRoles(logged_id);
    }

    @GetMapping("/user_info")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public UserDTO getUserInfo(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserInfo(username);
    }

    @GetMapping("/employee_info")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public EmployeeDTO getEmployeeInfo(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getEmployeeInfo(username);
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

    @GetMapping("/admin/all-users")
    @PreAuthorize("hasRole('Admin')")
    public List<EmployeeUserDTO> findUsersEmployeesForAdmin(){
        return userService.getEmployeeUserAdmin();
    }

    @GetMapping("/admin/{user}")
    @PreAuthorize("hasRole('Admin')")
    public EmployeeUserDTO findUsersEmployeesForAdminById(@PathVariable String user){
        Integer userId = userService.getUserInfo(user).getEmployeeId();
        return userService.getEmployeeUserById(userId);
    }

    @PostMapping("/createAccount")
    @PreAuthorize("hasRole('Admin')")
    public UserDTO createAccount(@RequestBody UserDTO userDTO){
        userDTO.setId(0);
        return userService.createAccount(userDTO);
    }

    @PutMapping("/admin/{userId}")
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

    @PutMapping("/{userid}/changeEnabled")
    @PreAuthorize("hasRole('Admin')")
    public UserDTO activateDeactivateUser(@PathVariable Integer userid){
        return userService.activateDeactivateUser(userid);
    }
}