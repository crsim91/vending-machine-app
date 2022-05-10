package com.example.vendingmachine.service;

import com.example.vendingmachine.dto.BuyDTO;
import com.example.vendingmachine.dto.BuyResponseDTO;
import com.example.vendingmachine.dto.DepositDTO;
import com.example.vendingmachine.dto.DepositResetDTO;

public interface ActionService {

	DepositDTO deposit(DepositDTO deposit);

	BuyResponseDTO buy(BuyDTO buy);

	DepositResetDTO reset();

}