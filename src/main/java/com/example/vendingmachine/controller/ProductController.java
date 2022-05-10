package com.example.vendingmachine.controller;

import com.example.vendingmachine.dto.DeleteResponseDTO;
import com.example.vendingmachine.model.Product;
import com.example.vendingmachine.service.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

	private final ProductServiceImpl productServiceImpl;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		return ResponseEntity.ok(productServiceImpl.createProduct(product));
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Product>> getProducts() {
		return ResponseEntity.ok(productServiceImpl.getProducts());
	}

	@GetMapping(value = "/{productId}", produces = "application/json")
	public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId) {
		return ResponseEntity.ok(productServiceImpl.getProduct(productId));
	}

	@PutMapping(value = "/{productId}", produces = "application/json")
	public ResponseEntity<Product> updateProduct(@PathVariable("productId") Long productId, @Valid @RequestBody Product product) {
		return ResponseEntity.ok(productServiceImpl.updateProduct(productId, product));
	}

	@DeleteMapping(value = "/{productId}", produces = "application/json")
	public ResponseEntity<DeleteResponseDTO> deleteProduct(@PathVariable("productId") Long productId) {
		return ResponseEntity.ok(productServiceImpl.deleteProduct(productId));
	}

}
