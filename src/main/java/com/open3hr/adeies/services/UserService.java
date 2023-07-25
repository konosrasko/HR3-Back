package com.open3hr.adeies.services;

import com.open3hr.adeies.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO findById(Long id);

    List<UserDTO> findAll();

    UserDTO save(UserDTO userDTO);

    void deleteById(Long id);
}