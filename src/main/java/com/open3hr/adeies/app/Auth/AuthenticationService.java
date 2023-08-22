package com.open3hr.adeies.app.Auth;
import com.open3hr.adeies.app.user.entity.User;
import com.open3hr.adeies.app.user.repository.UserRepository;
import com.open3hr.adeies.app.user.service.impl.UserServiceImpl;
import com.open3hr.adeies.configuration.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final String AES_SECRET_KEY = "ba6d59d38168f98b";
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

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Authentication authentication;
     String encryptedUsername = authenticationRequest.getUsername();
     String encryptedPassword = authenticationRequest.getPassword();


        // DECRYPTION
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] secretKeySpec = sha.digest(AES_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        secretKeySpec = Arrays.copyOf(AES_SECRET_KEY.getBytes(StandardCharsets.UTF_8), 16);
        SecretKey secretKey = new SecretKeySpec(secretKeySpec, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        String decryptedUsername = new String(cipher.doFinal(Base64.decodeBase64(encryptedUsername)), StandardCharsets.UTF_8).replace("\"", "");
        String decryptedPassword = new String(cipher.doFinal(Base64.decodeBase64(encryptedPassword)), StandardCharsets.UTF_8).replace("\"", "");

        authenticationRequest.setUsername(decryptedUsername); //update request info (username)
        authenticationRequest.setPassword(decryptedPassword); //(password)



        //AUTHENTICATION
        try {
          authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Λάθος Όνομα χρήστη/Κωδικός");
        } catch (DisabledException disabledException) {
            throw  new DisabledException("Ο χρήστης δεν είναι εγγεγραμμένος");
        }


        //GENERATE TOKEN

        User loggedUser = userRepository.findUserByUsername(authenticationRequest.getUsername()).orElseThrow();
        UserDetails user = new User(authenticationRequest.getUsername(), authenticationRequest.getPassword(), new ArrayList<>()) ;

        if(loggedUser.isLoggedIn() == false )
        {
            loggedUser.setLoggedIn(true);
            userRepository.save(loggedUser);
            var token = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(token).message("Welcome").build();
        }
        else
        {
            return AuthenticationResponse.builder().message("YOU ARE ALREADY LOGGED IN FROM ANOTHER SOURCE").build();
        }
    }

}
