package com.open3hr.adeies.app.user.service.impl;

import com.open3hr.adeies.app.Auth.LogoutRequest;
import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.exceptions.ConflictException;
import com.open3hr.adeies.app.exceptions.NotFoundException;
import com.open3hr.adeies.app.user.dto.EmployeeUserDTO;
import com.open3hr.adeies.app.user.dto.RolesDTO;
import com.open3hr.adeies.app.user.dto.UserDTO;
import com.open3hr.adeies.app.user.entity.User;
import com.open3hr.adeies.app.user.repository.UserRepository;
import com.open3hr.adeies.app.user.service.UserService;
import com.open3hr.adeies.configuration.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtService jwtService;


    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public UserDTO findById(Integer id) {
        Optional<User> myUser = userRepository.findById(id);
        if (myUser.isPresent()) {
            return new UserDTO(myUser.get());
        } else {
            throw new NotFoundException("Δε βρέθηκε χρήστης με το ζητούμενο id: " + id);
        }
    }

    @Override
    public UserDTO createAccount(UserDTO userDTO) {
        Optional<Employee> myEmployee = employeeRepository.findById(userDTO.getEmployeeId());
        if (myEmployee.isPresent()) {
            List<User> userList = userRepository.findAll();
            for (User user : userList) {
                if (Objects.equals(userDTO.getEmployeeId(), user.getEmployee().getId())) {
                    throw new ConflictException("Ο εργαζόμενος με id " + userDTO.getEmployeeId() + " έχει ήδη account.");
                }
            }
            userDTO.setPassTemp(true);
            userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
            userRepository.save(new User(userDTO, myEmployee.get()));
            return userDTO;
        } else throw new NotFoundException("Δε βρέθηκε ο επιλεγμένος εργαζόμενος.");
    }

    @Override
    public UserDTO updateStatus(Integer id) {
        Optional<User> myUser = userRepository.findById(id);
        if (myUser.isPresent()) {
            myUser.get().setEnable(!myUser.get().isEnable());
            userRepository.save(myUser.get());
            return new UserDTO(myUser.get());
        }
        throw new NotFoundException("Δε βρέθηκε χρήστης με το ζητούμενο id: " + id);
    }

    @Override
    public UserDTO changeSupervisorRights(Integer id) {
        Optional<User> myUser = userRepository.findById(id);
        if (myUser.isPresent()) {
            userRepository.save(myUser.get());
            return new UserDTO(myUser.get());
        }
        throw new NotFoundException("Δε βρέθηκε χρήστης με το ζητούμενο id: " + id);
    }

    @Override
    public UserDTO assignUserToEmployee(Integer userId, Integer employeeId) {
        Optional<Employee> myEmployee = employeeRepository.findById(employeeId);
        Optional<User> myUser = userRepository.findById(userId);
        if (myUser.isPresent()) {
            if (myEmployee.isPresent()) {
                myUser.get().setEmployee(myEmployee.get());
                userRepository.save(myUser.get());
                return new UserDTO(myUser.get());
            } else {
                throw new NotFoundException("Δε βρέθηκε εργαζόμενος με το ζητούμενο id: " + employeeId);
                // ### probably the employee ID is wrong ###
            }
        } else {
            throw new NotFoundException("Δε βρέθηκε χρήστης με το ζητούμενο id: " + userId);
            // ### probably the user ID is wrong ###
        }

    }

    @Override
    public UserDTO unassignUserAccount(Integer userId) {
        Optional<User> myUser = userRepository.findById(userId);
        if (myUser.isPresent()) {
            myUser.get().setEmployee(null);
            userRepository.save(myUser.get());
            return new UserDTO(myUser.get());
        } else {
            throw new NotFoundException("Δε βρέθηκε χρήστης με το ζητούμενο id: " + userId);
            // ### couldn't find user account with given id ###
        }
    }

    @Override
    public RolesDTO getUserRoles(Integer userId) {
        Optional<User> myUser = userRepository.findById(userId);
        if (myUser.isPresent()) {
            return new RolesDTO(myUser.get());
        } else {
            throw new NotFoundException("Δε βρέθηκε χρήστης με το ζητούμενο id: " + userId);
        }
    }

    @Override
    public EmployeeDTO getEmployeeInfo(String username) {
        for (User user : userRepository.findAll()) {
            if (user.getUsername().equals(username)) {
                Optional<Employee> myEmployee = employeeRepository.findById(user.getEmployee().getId());
                if (myEmployee.isPresent()) {
                    return new EmployeeDTO(myEmployee.get());
                }
            }
        }
        return null;
    }

    @Override
    public UserDTO getUserInfo(String username) {
        Optional<User> foundUser = userRepository.findAll()
                .stream()
                .filter(user -> Objects.equals(user.getUsername(), username))
                .findFirst();
        if (foundUser.isPresent()) {
            return new UserDTO(foundUser.get());
        } else throw new NotFoundException("Δε βρέθηκε χρήστης με το ζητούμενο username: " + username);
    }

    @Override
    public List<EmployeeUserDTO> getEmployeeUserAdmin() {
        List<User> users = userRepository.findAll();
        System.out.println(users);
        return userRepository.findAll()
                .stream()
                .map(user -> new EmployeeUserDTO(user.getEmployee(), user))
                .toList();
    }

    @Override
    public EmployeeUserDTO getEmployeeUserById(int userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {
            return new EmployeeUserDTO(foundUser.get().getEmployee(), foundUser.get());
        } else throw new NotFoundException("Δε βρέθηκε χρήστης με το ζητούμενο id: " + userId);
    }

    @Override
    public UserDTO editUser(UserDTO userDTO, Integer userId, boolean isPassEdited) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {

            //Σε περίπτωση που αφαιρούμε το δικαίωμα supervisor του χρήστη:
            boolean userWasSupervisor = foundUser.get().isSupervisor();
            if (userWasSupervisor && !userDTO.isSupervisor()){
                Optional<Employee> supervisor = Optional.empty(); //o supervisor του supervisor
                try {
                    //ευρεση του supervisor του user (optional)
                    supervisor = employeeRepository.findById(employeeRepository.findById(userDTO.getEmployeeId()).get().getSupervisorId());
                } catch (Exception e){System.out.println(e);}
                finally {
                    List<Employee> subordinates = employeeRepository.findAllSubordinatesOf(userDTO.getEmployeeId());
                    for (Employee subordinate: subordinates) {
                        //αν ο supervisor εχει supervisor τότε τον κληρονωμούν οι subordinates
                        if (supervisor.isPresent() && !Objects.equals(supervisor.get().getId(), userDTO.getEmployeeId()))
                            subordinate.setSupervisorId(supervisor.get().getId());
                            //αλλιώς μένουν χωρίς supervisor
                        else subordinate.setSupervisorId(null);
                    }
                }
            }

            userDTO.setId(userId);
            if(isPassEdited){
                userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
            }
            return new UserDTO(userRepository.save(new User(userDTO, foundUser.get().getEmployee())));
        } else throw new NotFoundException("Δε βρέθηκε χρήστης με το ζητούμενο id: " + userId);
    }

    @Override
    public UserDTO activateDeactivateUser(Integer userId) {
        Optional<User> myUser = userRepository.findById(userId);
        if (myUser.isPresent()) {
            myUser.get().setEnable(!myUser.get().isEnable());
            userRepository.save(myUser.get());
            return new UserDTO(myUser.get());
        } else {
            throw new NotFoundException("Δε βρέθηκε χρήστης με το ζητούμενο id: " + userId);
        }
    }

    @Override
    public String userLogout(LogoutRequest logoutRequest) {

        User user = userRepository.findUserByUsername(logoutRequest.getUsername()).orElseThrow();
        user.setLoggedIn(false);
        userRepository.save(user);

        return ("you have been logged out succesfully");
    }
}
