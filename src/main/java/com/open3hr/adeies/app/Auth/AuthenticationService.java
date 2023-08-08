package com.open3hr.adeies.app.Auth;

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
import org.springframework.security.core.userdetails.User;
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
        System.out.println("encrypted username: " +  encryptedUsername + " and encypted pass: " +  encryptedPassword);

        //DECRYPTION
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] secretKeySpec = sha.digest(AES_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        secretKeySpec = Arrays.copyOf(AES_SECRET_KEY.getBytes(StandardCharsets.UTF_8), 16);
        SecretKey secretKey = new SecretKeySpec(secretKeySpec, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        String decryptedUsername = new String(cipher.doFinal(Base64.decodeBase64(encryptedUsername)), StandardCharsets.UTF_8).replace("\"", "");
        String decryptedPassword = new String(cipher.doFinal(Base64.decodeBase64(encryptedPassword)), StandardCharsets.UTF_8).replace("\"", "");
        System.out.println("Updating request credentials with decrypted data (o Simos einai paneksipnos)");
        authenticationRequest.setUsername(decryptedUsername); //update request info (username)
        authenticationRequest.setPassword(decryptedPassword); //(password)
        System.out.println("decrypted username: " + decryptedUsername + " and pass " +  decryptedPassword);


        //AUTHENTICATION
        try {
          authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(decryptedUsername,decryptedPassword));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            throw  new DisabledException("User not Registered");
        }


        //GENERATE TOKEN
        User user = new User(authenticationRequest.getUsername(), authenticationRequest.getPassword(),new ArrayList<>());
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }
}
