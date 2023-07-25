package com.open3hr.adeies.controllers;

import com.open3hr.adeies.dto.UserDTO;
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

    @PostMapping("/users")
    public UserDTO save(@RequestBody UserDTO userDTO){
        userDTO.setId(0);
        return userService.save(userDTO);
    }

    @PutMapping("/users")
    public UserDTO update(@RequestBody UserDTO userDTO){
        return userService.save(userDTO);
    }

    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable Integer id){
        userService.deleteById(id);
    }
}


