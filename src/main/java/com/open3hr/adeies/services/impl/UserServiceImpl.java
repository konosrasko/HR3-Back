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

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return new UserDTO(userOptional.get());
        } else {
            throw new RuntimeException("Couldn't find user with id: " + id);
        }
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        return new UserDTO(userRepository.save(userDTO.toUser()));
    }

    @Override
    public void deleteById(int id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }
    }
