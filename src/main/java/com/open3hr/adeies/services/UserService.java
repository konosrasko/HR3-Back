package com.open3hr.adeies.services;

import com.open3hr.adeies.dto.UserDTO;
import java.util.List;

public interface UserService {
    public UserDTO findById(int id);

    List<UserDTO> findAll();

    UserDTO save(UserDTO userDTO);

    void deleteById(int id);
}
