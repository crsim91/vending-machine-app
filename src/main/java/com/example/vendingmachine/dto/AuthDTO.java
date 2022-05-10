package com.example.vendingmachine.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class AuthDTO {
	@NotBlank(message = "Username must be not null")
	private String username;
	@NotBlank(message = "Password must be not null")
	private String password;
}
