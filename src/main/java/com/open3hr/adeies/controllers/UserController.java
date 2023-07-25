package com.open3hr.adeies.controllers;

import com.open3hr.adeies.dto.UserDTO;
import com.open3hr.adeies.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
        @Autowired
        private UserServiceImpl userService;


        @GetMapping("/{id}" )
        @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
        public UserDTO getUserById(@PathVariable int id){
            return userService.findById(id);
        }

        @GetMapping("/all")
        public List<UserDTO> findAll(){
                return userService.findAll();
        }


        @PostMapping("/add")
        public UserDTO save(@RequestBody UserDTO userDTO){
                userDTO.setId(0);
                return userService.save(userDTO);
        }

        @PutMapping("/edit")
        public UserDTO update(@RequestBody UserDTO userDTO){
                return userService.save(userDTO);
        }

        @DeleteMapping("/{id}")
        public void deleteById(@PathVariable int id){
                userService.deleteById(id);
        }
}


