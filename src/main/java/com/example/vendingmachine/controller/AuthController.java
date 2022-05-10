package com.example.vendingmachine.controller;

import com.example.vendingmachine.dto.AuthDTO;
import com.example.vendingmachine.security.JWTToken;
import com.example.vendingmachine.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<JWTToken> login(@Valid @RequestBody AuthDTO authDto, HttpServletResponse response) {
		return ResponseEntity.ok(authService.login(authDto, response));
	}

}