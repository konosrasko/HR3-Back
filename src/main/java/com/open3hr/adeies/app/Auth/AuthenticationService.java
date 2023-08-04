package com.open3hr.adeies.app.Auth;

import com.open3hr.adeies.app.enums.Role;
import com.open3hr.adeies.app.user.repository.UserRepository;
import com.open3hr.adeies.app.user.service.UserService;
import com.open3hr.adeies.app.user.service.impl.UserServiceImpl;
import com.open3hr.adeies.configuration.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserServiceImpl userServiceimpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest)  throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        Authentication authentication;

        try {
          authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            throw  new DisabledException("User not Registered");
        }


        User user = new User(authenticationRequest.getUsername(), authenticationRequest.getPassword(),authentication.getAuthorities());
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();

    }
}
