package com.example.vendingmachine.controller;

import com.example.vendingmachine.dto.BuyDTO;
import com.example.vendingmachine.dto.BuyResponseDTO;
import com.example.vendingmachine.dto.DepositDTO;
import com.example.vendingmachine.dto.DepositResetDTO;
import com.example.vendingmachine.service.ActionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/actions")
@AllArgsConstructor
public class ActionController {

	private final ActionServiceImpl actionServiceImpl;

	@PostMapping(value = "/deposit", consumes = "application/json", produces = "application/json")
	public ResponseEntity<DepositDTO> deposit(@Valid @RequestBody DepositDTO deposit) {
		return ResponseEntity.ok(actionServiceImpl.deposit(deposit));
	}

	@PostMapping(value = "/buy", consumes = "application/json", produces = "application/json")
	public ResponseEntity<BuyResponseDTO> buy(@Valid @RequestBody BuyDTO buy) {
		return ResponseEntity.ok(actionServiceImpl.buy(buy));
	}

	@GetMapping(value = "/reset", produces = "application/json")
	public ResponseEntity<DepositResetDTO> reset() {
		return ResponseEntity.ok(actionServiceImpl.reset());
	}

}
