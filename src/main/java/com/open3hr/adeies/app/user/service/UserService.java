package com.open3hr.adeies.app.user.service;

import com.open3hr.adeies.app.user.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO findById(Integer id);

    List<UserDTO> findAll();

    void deleteById(Integer id);

    UserDTO createAccount(UserDTO userDTO);

    UserDTO updateStatus(Integer id);
    UserDTO changeSupervisorRights(Integer id);

    UserDTO assignUserToEmployee(Integer userId, Integer employeeId);

    UserDTO unassignUserAccount(Integer userId);
}
