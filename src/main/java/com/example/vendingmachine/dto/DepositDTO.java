package com.example.vendingmachine.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DepositDTO {
	@NotNull(message = "Amount must be not null")
	private Integer amount;
}
