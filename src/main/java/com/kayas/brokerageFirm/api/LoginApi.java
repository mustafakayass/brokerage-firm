package com.kayas.brokerageFirm.api;

import com.kayas.brokerageFirm.dto.request.LoginRequest;
import com.kayas.brokerageFirm.dto.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface LoginApi {

    ResponseEntity<JwtResponse> login(LoginRequest loginRequest);
}
