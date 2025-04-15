package com.victot.desafio_dev_jr_apse.controller;

import com.victot.desafio_dev_jr_apse.dto.LoginRequest;
import com.victot.desafio_dev_jr_apse.dto.UserRequest;
import com.victot.desafio_dev_jr_apse.model.User;
import com.victot.desafio_dev_jr_apse.model.exception.UserAlreadyExistsException;
import com.victot.desafio_dev_jr_apse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;



    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest userDTO){
        if(userRepository.existsByUsername(userDTO.getUsername())){
            throw new UserAlreadyExistsException("O nome de usuário: " + userDTO.getUsername() + " não está disponível");
        }

        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        user.setPassword(encoder.encode(userDTO.getPassword()));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword())
        );

        return ResponseEntity.ok().body("Você autenticou com sucesso!");
    }



}
