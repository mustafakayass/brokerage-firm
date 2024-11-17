package com.kayas.brokerageFirm.controller;

import com.kayas.brokerageFirm.api.LoginApi;
import com.kayas.brokerageFirm.dto.response.JwtResponse;
import com.kayas.brokerageFirm.dto.request.LoginRequest;
import com.kayas.brokerageFirm.service.UserService;
import com.kayas.brokerageFirm.utility.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController implements LoginApi {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, 
                         JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), 
                loginRequest.getPassword()
            )
        );

        if (authentication.isAuthenticated()) {
            String token = jwtTokenUtil.generateToken(loginRequest.getUsername());
            return ResponseEntity.ok(new JwtResponse(token));
        }

        return ResponseEntity.badRequest().build();
    }
}
