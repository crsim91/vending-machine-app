package com.example.vendingmachine.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuyResponseDTO {
	private Integer totalSpent;
	private String productName;
	private List<Integer> change;
}
