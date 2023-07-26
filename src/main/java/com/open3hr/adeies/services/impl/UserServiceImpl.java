package com.open3hr.adeies.services.impl;

import com.open3hr.adeies.dto.EmployeeDTO;
import com.open3hr.adeies.dto.UserDTO;
import com.open3hr.adeies.entities.Employee;
import com.open3hr.adeies.entities.User;
import com.open3hr.adeies.repositories.EmployeeRepository;
import com.open3hr.adeies.repositories.UserRepository;
import com.open3hr.adeies.services.EmployeeService;
import com.open3hr.adeies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .toList();
    }

    @Override
    public UserDTO findById(Integer id) {
        Optional<User> myUser = userRepository.findById(id);
        if (myUser.isPresent()) {
            return new UserDTO(myUser.get());
        } else {
            throw new RuntimeException("Couldn't find user with id: " + id);
        }
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    @Override
    public UserDTO createAccount(UserDTO userDTO) {
        Optional<Employee> myEmployee = employeeRepository.findById(userDTO.getEmployeeId());
        if (myEmployee.isPresent()) {
            List<User> userList = userRepository.findAll();
            for (User user : userList){
                if(Objects.equals(userDTO.getEmployeeId(), user.getEmployee().getId())){
                    throw new RuntimeException("This Employee has already a User Account");
                }
            }
            userRepository.save(new User(userDTO, myEmployee.get()));
            return userDTO;
        } else throw new RuntimeException("Employee not found, couldn't create new account.");
    }
}