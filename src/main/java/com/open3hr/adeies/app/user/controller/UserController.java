package com.open3hr.adeies.app.user.controller;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.user.dto.EmployeeUserDTO;
import com.open3hr.adeies.app.user.dto.RolesDTO;
import com.open3hr.adeies.app.user.dto.UserDTO;
import com.open3hr.adeies.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RolesDTO> getMyRoles(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        int logged_id = userService.getUserInfo(username).getId();
        return new ResponseEntity<>(userService.getUserRoles(logged_id), HttpStatus.OK);
    }

    //used in: http://localhost:4200/home/admin
    @GetMapping("/admin/{userId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<EmployeeUserDTO> findUsersEmployeesForAdminById(@PathVariable Integer userId){
        System.out.println(userId);
        return new ResponseEntity<>(userService.getEmployeeUserById(userId),HttpStatus.OK);
    }

    //used in: http://localhost:4200/login
    @GetMapping("/user_info")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public ResponseEntity<UserDTO> getUserInfo(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(userService.getUserInfo(username),HttpStatus.OK);
    }

    @GetMapping("/employee_info")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public ResponseEntity<EmployeeDTO> getEmployeeInfo(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(userService.getEmployeeInfo(username),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('HR') OR hasRole('Employee') OR hasRole('Admin')")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
        return new ResponseEntity<>(userService.findById(id),HttpStatus.OK);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('HR') OR hasRole('Admin')")
    public ResponseEntity<List<UserDTO>> findAll(){
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/admin/all-users")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<EmployeeUserDTO>> findUsersEmployeesForAdmin(){
        return new ResponseEntity<>(userService.getEmployeeUserAdmin(),HttpStatus.OK);
    }


    @PostMapping("/createAccount")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<UserDTO> createAccount(@RequestBody UserDTO userDTO){
        userDTO.setId(0);
        return new ResponseEntity<>(userService.createAccount(userDTO),HttpStatus.OK);
    }

    @PutMapping("/admin/{userId}/{isPassEdited}")
    @PreAuthorize("hasRole('HR') OR hasRole('Admin') OR hasRole('Employee')")
    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDTO, @PathVariable Integer userId, @PathVariable boolean isPassEdited){
        return new ResponseEntity<>(userService.editUser(userDTO, userId, isPassEdited),HttpStatus.OK);
    }

    @PutMapping("/{id}/changeStatus")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<UserDTO> changeStatus(@PathVariable Integer id){
        return new ResponseEntity<>(userService.updateStatus(id),HttpStatus.OK);
    }

    //TI EINAI AYTO
    @PutMapping("/{id}/supervisorRights")
    public ResponseEntity<UserDTO> supervisorRights(@PathVariable Integer id){
        return new ResponseEntity<>(userService.changeSupervisorRights(id),HttpStatus.OK);
    }

    @PutMapping("/{userId}/assignToEmployee/{employeeId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<UserDTO> assignUserToEmployee(@PathVariable Integer userId, @PathVariable Integer employeeId){
        return new ResponseEntity<>(userService.assignUserToEmployee(userId,employeeId),HttpStatus.OK);
    }

    @PutMapping("/{userId}/unassign")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<UserDTO> unassignUserAccount(@PathVariable Integer userId){
        return new ResponseEntity<>(userService.unassignUserAccount(userId),HttpStatus.OK);
    }

    @PutMapping("/{userid}/changeEnabled")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<UserDTO> activateDeactivateUser(@PathVariable Integer userid){
        return new ResponseEntity<>(userService.activateDeactivateUser(userid),HttpStatus.OK);
    }
}
