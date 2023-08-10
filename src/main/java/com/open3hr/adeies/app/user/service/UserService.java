package com.open3hr.adeies.app.user.service;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.user.dto.EmployeeUserDTO;
import com.open3hr.adeies.app.user.dto.RolesDTO;
import com.open3hr.adeies.app.user.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findById(Integer id);

    List<UserDTO> findAll();

    UserDTO createAccount(UserDTO userDTO);

    UserDTO updateStatus(Integer id);
    UserDTO changeSupervisorRights(Integer id);

    UserDTO assignUserToEmployee(Integer userId, Integer employeeId);

    UserDTO unassignUserAccount(Integer userId);

    RolesDTO getUserRoles(Integer userId);
    UserDTO getUserInfo(String username);
    EmployeeDTO getEmployeeInfo(String username);

    List<EmployeeUserDTO> getEmployeeUserAdmin();

    EmployeeUserDTO getEmployeeUserById(int userId);

    UserDTO editUser(UserDTO userDTO, Integer userId);

    UserDTO activateDeactivateUser(Integer userId);
}
