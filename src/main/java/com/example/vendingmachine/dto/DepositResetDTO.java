package com.example.vendingmachine.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DepositResetDTO {
	private Integer amount;
	private List<Integer> change;
}
