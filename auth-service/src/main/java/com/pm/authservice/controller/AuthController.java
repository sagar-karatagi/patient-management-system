package com.pm.authservice.controller;


import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authservice;

    AuthController(AuthService authservice){
        this.authservice = authservice;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){

        Optional<String> tokenOptional = authservice.authenticate(loginRequestDTO);

        return tokenOptional
                .map(
                        s -> ResponseEntity.ok(new LoginResponseDTO(s)))
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    //Validate token
    @GetMapping("/validate")
    public ResponseEntity<Void> validate (@RequestHeader("Authorization") String authHeader){

        if(authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return  authservice.validateToken(authHeader.substring(7)) ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}
