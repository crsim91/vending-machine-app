package com.example.vendingmachine.service;

import com.example.vendingmachine.dto.DeleteResponseDTO;
import com.example.vendingmachine.model.Product;

import java.util.List;

public interface ProductService {

	Product createProduct(Product product);

	Product getProduct(Long productId);

	List<Product> getProducts();

	Product updateProduct(Long productId, Product product);

	DeleteResponseDTO deleteProduct(Long productId);

}