package com.example.vendingmachine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "machine_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

	@Id
	@SequenceGenerator(
			name = "product_id_sequence",
			sequenceName = "product_id_sequence"
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "product_id_sequence"
	)
	private Long id;

	@Column(name = "amount_available")
	@NotNull(message = "Amount available must be not null")
	private Integer amountAvailable;

	@Column(name = "cost")
	@NotNull(message = "Cost must be not null")
	private Integer cost;

	@Column(name = "product_name", unique = true)
	@NotBlank(message = "Product name must be not empty")
	private String productName;

	@Column(name = "seller_id")
	private Long sellerId;

}
