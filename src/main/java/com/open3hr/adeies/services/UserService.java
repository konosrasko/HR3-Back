package com.open3hr.adeies.services;

import com.open3hr.adeies.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO findById(Integer id);

    List<UserDTO> findAll();

    void deleteById(Integer id);

    UserDTO createAccount(UserDTO userDTO);

    UserDTO updateStatus(Integer id);
    UserDTO changeSupervisorRights(Integer id);
}
