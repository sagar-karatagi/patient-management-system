package com.pm.authservice.service;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.model.User;
import com.pm.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService  {
        private final UserService userservice;
        private final PasswordEncoder passwordEncoder ;
        private final JwtUtil jwtUtil ;
        public AuthService(UserService userservice,PasswordEncoder passwordEncoder,JwtUtil jwtutil){
            this.passwordEncoder = passwordEncoder;
            this.userservice = userservice;
            this.jwtUtil = jwtutil;
        }
        public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {

            Optional<String> token = userservice.findByEmail(loginRequestDTO.getEmail())
                    .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(),u.getPassword()))
                    .map(u->jwtUtil.generateToken(u.getEmail(),u.getRole());
            return token;
        }

    public boolean validateToken(String token) {

            try{
                jwtUtil.validateToken(token);
                return true;
            }catch (JwtException e){
                return  false;
            }
    }
}
