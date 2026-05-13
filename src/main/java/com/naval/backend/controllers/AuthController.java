package com.naval.backend.controllers;

import com.naval.backend.dto.ForgotPasswordRequest;
import com.naval.backend.dto.GoogleAuthRequest;
import com.naval.backend.dto.LoginRequest;
import com.naval.backend.dto.LoginResponse;
import com.naval.backend.dto.RegisterRequest;
import com.naval.backend.dto.ResetPasswordRequest;
import com.naval.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired private AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<LoginResponse> registrar(@RequestBody RegisterRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(request));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/google")
  public ResponseEntity<LoginResponse> loginConGoogle(@RequestBody GoogleAuthRequest request) {
    return ResponseEntity.ok(authService.loginConGoogle(request.getAccessToken()));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
    authService.forgotPassword(request.getEmail());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
    authService.resetPassword(request.getToken(), request.getNuevaPassword());
    return ResponseEntity.ok().build();
  }
}
