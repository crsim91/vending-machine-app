package com.example.vendingmachine.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteResponseDTO {
	private String description = "Successfully deleted";
}
