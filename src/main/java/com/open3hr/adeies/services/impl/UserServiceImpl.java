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
    public UserDTO save(UserDTO userDTO) {
        User myUser = new User(userDTO);
        return new UserDTO(userRepository.save(myUser));
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    @Override
    public UserDTO createAccount(UserDTO userDTO) {
        Optional<Employee> myEmployee = employeeRepository.findById(userDTO.getEmployeeId());
        if (myEmployee.isPresent()) {
            userRepository.save(new User(userDTO));
            return userDTO;
        } else {
            throw new RuntimeException("Employee not found, couldn't create new account.");
        }


    }

    @Override
    public UserDTO updateStatus(Integer id) {
        Optional<User> myUser = userRepository.findById(id);
        if(myUser.isPresent()){
            myUser.get().setIsEnabled(!myUser.get().getIsEnabled());
            userRepository.save(myUser.get());
            return new UserDTO(myUser.get());
        }
        throw new RuntimeException("Couldn't find this user with id "+id);
    }

    @Override
    public UserDTO changeSupervisorRights(Integer id) {
        Optional<User> myUser = userRepository.findById(id);
        if(myUser.isPresent()){
                myUser.get().setIsSupervisor(!myUser.get().getIsSupervisor());
                userRepository.save(myUser.get());
                return new UserDTO(myUser.get());
            }
        throw new RuntimeException("Couldn't find this user with id "+id);
    }
}