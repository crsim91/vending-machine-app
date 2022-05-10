package com.example.vendingmachine.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BuyDTO {
	@NotNull(message = "ProductId must be not null")
	private Long productId;
	@NotNull(message = "Amount of products must be not null")
	private Integer amountOfProducts;
}
