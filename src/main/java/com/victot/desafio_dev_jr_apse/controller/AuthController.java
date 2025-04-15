package com.victot.desafio_dev_jr_apse.controller;

import com.victot.desafio_dev_jr_apse.dto.AuthResponse;
import com.victot.desafio_dev_jr_apse.dto.LoginRequest;
import com.victot.desafio_dev_jr_apse.dto.UserRequest;
import com.victot.desafio_dev_jr_apse.model.User;
import com.victot.desafio_dev_jr_apse.model.exception.UserAlreadyExistsException;
import com.victot.desafio_dev_jr_apse.repository.UserRepository;
import com.victot.desafio_dev_jr_apse.service.JwtService;
import com.victot.desafio_dev_jr_apse.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest userDTO){
        if(userRepository.existsByUsername(userDTO.getUsername())){
            throw new UserAlreadyExistsException("O nome de usuário: " + userDTO.getUsername() + " não está disponível");
        }

        // Criptografar a senha
        String encodedPassword = encoder.encode(userDTO.getPassword());

        // Criar o usuário
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encodedPassword);  // Salva a senha criptografada
        user.setEmail(userDTO.getEmail());

        // Salvar o usuário
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword())
        );

        final UserDetails user = userDetailsServiceImpl.loadUserByUsername(loginRequest.getUsername());
        final String token = jwtService.generateToken(user);

        return ResponseEntity.ok().body(new AuthResponse(token));
    }



}
