package com.open3hr.adeies.app.user.service.impl;

import com.open3hr.adeies.app.user.dto.EmployeeUserDTO;
import com.open3hr.adeies.app.user.dto.UserDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.user.entity.User;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.user.repository.UserRepository;
import com.open3hr.adeies.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("we are in user detail service");
     User user = userRepository.findUserByUsername(username).orElseThrow();
       return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getAuthorities());
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
                userRepository.save(myUser.get());
                return new UserDTO(myUser.get());
            }
        throw new RuntimeException("Couldn't find this user with id "+id);
    }

    @Override
    public UserDTO assignUserToEmployee(Integer userId, Integer employeeId) {
        Optional<Employee> myEmployee = employeeRepository.findById(employeeId);
        Optional<User> myUser = userRepository.findById(userId);
        if(myUser.isPresent()){
            if(myEmployee.isPresent()){
                myUser.get().setEmployee(myEmployee.get());
                userRepository.save(myUser.get());
                return new UserDTO(myUser.get());
            }else {
                throw new RuntimeException("Couldn't find employee!");
                // ### probably the employee ID is wrong ###
            }
        }else{
            throw new RuntimeException("Couldn't find the user account");
            // ### probably the user ID is wrong ###
        }

    }

    @Override
    public UserDTO unassignUserAccount(Integer userId) {
        Optional<User> myUser = userRepository.findById(userId);
        if(myUser.isPresent()){
            myUser.get().setEmployee(null);
            userRepository.save(myUser.get());
            return new UserDTO(myUser.get());
        }else {
            throw new RuntimeException("Couldn't find user account!");
            // ### couldn't find user account with given id ###
        }
    }

    @Override
    public UserDTO getUserInfo(String username) {
        for (User user : userRepository.findAll()){
            if (user.getUsername().equals(username))
                return new UserDTO(user);
        }
        return null;
    }

    @Override
    public List<EmployeeUserDTO> getEmployeeUserAdmin() {
        return userRepository.findAll()
                .stream()
                .map(user -> new EmployeeUserDTO(user.getEmployee(), user))
                .toList();
    }

    @Override
    public EmployeeUserDTO getEmployeeUserById(int userId){
        Optional<User> foundUser = userRepository.findById(userId);
        if(foundUser.isPresent()){
            return new EmployeeUserDTO(foundUser.get().getEmployee(), foundUser.get());
        }else throw new RuntimeException("This user does not exist");
    }

    @Override
    public UserDTO editUser(UserDTO userDTO, Integer userId){
        Optional<User> foundUser = userRepository.findById(userId);
        if(foundUser.isPresent()){
            userDTO.setId(userId);
            return new UserDTO(userRepository.save(new User(userDTO, foundUser.get().getEmployee())));
        }else throw new RuntimeException("This user does not exist");
    }
}