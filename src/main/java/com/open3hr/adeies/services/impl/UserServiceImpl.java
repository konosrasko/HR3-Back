package com.open3hr.adeies.services.impl;

import com.open3hr.adeies.dto.UserDTO;
import com.open3hr.adeies.entities.User;
import com.open3hr.adeies.repositories.UserRepository;
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
}
