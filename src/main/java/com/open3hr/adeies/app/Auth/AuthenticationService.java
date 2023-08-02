package com.open3hr.adeies.app.Auth;

import com.open3hr.adeies.app.enums.Role;
import com.open3hr.adeies.app.user.repository.UserRepository;
import com.open3hr.adeies.configuration.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    private JwtService jwtService;



    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));

        var user = userRepository.findUserByUsername(authenticationRequest.getUsername()).orElseThrow();
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }
}
