package com.booking_micro.AuthService.controller;

import com.booking_micro.AuthService.dto.LoginRequest;
import com.booking_micro.AuthService.dto.RegisterRequest;
import com.booking_micro.AuthService.dto.ResponseDto;
import com.booking_micro.AuthService.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/user")
    public ResponseEntity<ResponseDto<String>> registerUser(@RequestBody @Valid RegisterRequest registerRequest) throws Exception {
        String result = authService.registerUser(registerRequest);
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @PostMapping("/register/sp")
    public ResponseEntity<ResponseDto<String>> registerService(@RequestBody @Valid RegisterRequest registerRequest) throws Exception {
        String result = authService.registerService(registerRequest);
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<String>> login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .body(token)
                .build());
    }
}
