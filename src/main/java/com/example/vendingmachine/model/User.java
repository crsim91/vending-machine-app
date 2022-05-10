package com.example.vendingmachine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "machine_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@SequenceGenerator(
			name = "user_id_sequence",
			sequenceName = "user_id_sequence"
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "user_id_sequence"
	)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String username;

	@NotNull
	private String password;

	@NotNull
	private Integer deposit;

	@ElementCollection
	private Set<GrantedAuthority> roles;

}
