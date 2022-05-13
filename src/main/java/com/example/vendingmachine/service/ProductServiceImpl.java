package com.example.vendingmachine.service;

import com.example.vendingmachine.dto.DeleteResponseDTO;
import com.example.vendingmachine.exception.*;
import com.example.vendingmachine.model.Product;
import com.example.vendingmachine.model.User;
import com.example.vendingmachine.repository.ProductRepository;
import com.example.vendingmachine.repository.UserRepository;
import com.example.vendingmachine.util.CoinAmountUtil;
import com.example.vendingmachine.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	@Override
	@PreAuthorize("hasRole('ROLE_SELLER')")
	public Product createProduct(Product product) {
		User currentUser = getUserByUsername(SecurityUtils.getContextUsername());
		validateAmountType(product.getCost());

		try {
			product.setSellerId(currentUser.getId());
			log.info("Creating product {}", product);
			return productRepository.save(product);
		} catch (DataIntegrityViolationException e) {
			log.error("Product duplication {}", e.getMessage());
			throw new ProductConflictException(product.getProductName());
		}
	}

	@Override
	public Product getProduct(Long productId) {
		return getProductById(productId);
	}

	@Override
	public List<Product> getProducts() {
		return getAllProducts();
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SELLER')")
	public Product updateProduct(Long productId, Product product) {
		User currentUser = getUserByUsername(SecurityUtils.getContextUsername());
		Optional<Product> productById = productRepository.findById(productId);

		Long sellerId = currentUser.getId();
		sellerId = getProductSellerId(productById, sellerId);

		if (isUserProductSeller(sellerId, currentUser)) {
			validateAmountType(product.getCost());

			try {
				product.setId(productId);
				product.setSellerId(sellerId);
				log.info("Update product {}", product);
				return productRepository.save(product);
			} catch (DataIntegrityViolationException e) {
				log.error("Product duplication {}", e.getMessage());
				throw new ProductConflictException(product.getProductName());
			}
		}
		throw new UnauthorizedException();
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SELLER')")
	public DeleteResponseDTO deleteProduct(Long productId) {
		User currentUser = getUserByUsername(SecurityUtils.getContextUsername());
		Product productById = getProductById(productId);

		if (isUserProductSeller(productById.getSellerId(), currentUser)) {
			log.info("Delete product {}", productById);
			productRepository.delete(productById);
			return new DeleteResponseDTO();
		}
		throw new UnauthorizedException();
	}

	private boolean isUserProductSeller(Long productSellerId, User user) {
		return productSellerId.equals(user.getId());
	}

	private User getUserByUsername(String username) {
		return userRepository.findOneByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}

	private List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	private Product getProductById(Long productId) {
		return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
	}

	private void validateAmountType(Integer deposit) {
		if (!CoinAmountUtil.isValidAmount(deposit)) throw new InvalidAmountException(deposit);
	}

	private Long getProductSellerId(Optional<Product> productById, Long productOrUserIdSeller) {
		if (productById.isPresent()) {
			if (productById.get().getSellerId() != null) {
				productOrUserIdSeller = productById.get().getSellerId();
			}
		}
		return productOrUserIdSeller;
	}

}