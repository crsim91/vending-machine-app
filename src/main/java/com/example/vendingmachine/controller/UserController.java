package com.example.vendingmachine.controller;

import com.example.vendingmachine.dto.DeleteResponseDTO;
import com.example.vendingmachine.dto.ResponseUserDTO;
import com.example.vendingmachine.dto.UserDTO;
import com.example.vendingmachine.model.User;
import com.example.vendingmachine.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

	private final UserServiceImpl userServiceImpl;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO user) {
		return ResponseEntity.ok().body(userServiceImpl.createUser(user));
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<ResponseUserDTO>> getUsers() {
		return ResponseEntity.ok(userServiceImpl.getUsers());
	}

	@GetMapping(value = "/{userId}", produces = "application/json")
	public ResponseEntity<ResponseUserDTO> getUser(@PathVariable("userId") Long userId) {
		return ResponseEntity.ok(userServiceImpl.getUser(userId));
	}

	@PutMapping(value = "/{userId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserDTO user) {
		return ResponseEntity.ok(userServiceImpl.updateUser(userId, user));
	}

	@DeleteMapping(value = "/{userId}", produces = "application/json")
	public ResponseEntity<DeleteResponseDTO> deleteUser(@PathVariable("userId") Long userId) {
		return ResponseEntity.ok(userServiceImpl.deleteUser(userId));
	}

}