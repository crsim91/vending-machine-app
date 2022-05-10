package com.example.vendingmachine.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	@NotBlank(message = "Username must be not empty")
	@Pattern(regexp = "^[_'.@A-Za-z0-9-]*$")
	@Size(min = 6, max = 24)
	private String username;
	@NotBlank(message = "Password must be not empty")
	@Size(min = 4, max = 32)
	private String password;
	@NotNull(message = "Deposit must be not empty")
	private Integer deposit;
	@NotNull(message = "Role must be not empty")
	private Set<String> roles;
}
