package com.example.vendingmachine.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDTO {
	private Long id;
	private String username;
	private Integer deposit;
	private Set<String> roles;
}
