package com.example.vendingmachine.service;

import com.example.vendingmachine.dto.BuyDTO;
import com.example.vendingmachine.dto.BuyResponseDTO;
import com.example.vendingmachine.dto.DepositDTO;
import com.example.vendingmachine.dto.DepositResetDTO;
import com.example.vendingmachine.exception.*;
import com.example.vendingmachine.model.Product;
import com.example.vendingmachine.model.User;
import com.example.vendingmachine.repository.ProductRepository;
import com.example.vendingmachine.repository.UserRepository;
import com.example.vendingmachine.util.CoinAmountUtil;
import com.example.vendingmachine.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActionServiceImpl implements ActionService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	@Override
	@PreAuthorize("hasRole('ROLE_BUYER')")
	public DepositDTO deposit(DepositDTO deposit) {
		User currentUser = getUserByUsername(SecurityUtils.getContextUsername());
		validateCoinAmount(deposit.getAmount());

		return getDepositDTO(getUpdateDeposit(deposit.getAmount(), currentUser));
	}

	@Override
	@PreAuthorize("hasRole('ROLE_BUYER')")
	public BuyResponseDTO buy(BuyDTO buy) {
		User currentUser = getUserByUsername(SecurityUtils.getContextUsername());
		Product selectedProduct = getProductById(buy.getProductId());

		validateProductAmount(buy.getAmountOfProducts(), selectedProduct);
		validateFunds(buy.getAmountOfProducts(), currentUser, selectedProduct);

		BuyResponseDTO buyResponseDTO = getBuyResponseDTO(buy.getAmountOfProducts(), currentUser.getDeposit(), selectedProduct);
		updateAmounts(buy.getAmountOfProducts(), currentUser, selectedProduct);

		return buyResponseDTO;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_BUYER')")
	public DepositResetDTO reset() {
		User user = getUserByUsername(SecurityUtils.getContextUsername());
		var resetPrice = 0;

		DepositResetDTO depositResetDTO = getDepositResetDTO(resetPrice, user.getDeposit());
		resetDeposit(resetPrice, user);

		return depositResetDTO;
	}

	private BuyResponseDTO getBuyResponseDTO(Integer amountOfProducts, Integer userDeposit, Product product) {
		BuyResponseDTO buyResponseDTO = new BuyResponseDTO();
		buyResponseDTO.setProductName(product.getProductName());
		buyResponseDTO.setTotalSpent(getTotalSpent(amountOfProducts, product));
		buyResponseDTO.setChange(CoinAmountUtil.calculateCoinChange(userDeposit, getTotalSpent(amountOfProducts, product)));
		return buyResponseDTO;
	}

	private DepositResetDTO getDepositResetDTO(int price, Integer userDeposit) {
		DepositResetDTO depositResetDTO = new DepositResetDTO();
		depositResetDTO.setChange(CoinAmountUtil.calculateCoinChange(userDeposit, price));
		depositResetDTO.setAmount(price);
		return depositResetDTO;
	}

	private DepositDTO getDepositDTO(User savedDeposit) {
		DepositDTO depositDTO = new DepositDTO();
		depositDTO.setAmount(savedDeposit.getDeposit());
		return depositDTO;
	}

	private User getUpdateDeposit(Integer depositAmount, User user) {
		user.setDeposit(getDepositSum(depositAmount, user));
		User savedDeposit = userRepository.save(user);
		return savedDeposit;
	}

	private void updateAmounts(Integer amountOfProducts, User user, Product product) {
		user.setDeposit(user.getDeposit() - getTotalSpent(amountOfProducts, product));
		product.setAmountAvailable(product.getAmountAvailable() - amountOfProducts);
		userRepository.save(user);
		productRepository.save(product);
	}

	private void resetDeposit(int resetPrice, User user) {
		user.setDeposit(resetPrice);
		userRepository.save(user);
	}

	private int getTotalSpent(Integer amountOfProducts, Product product) {
		return amountOfProducts * product.getCost();
	}

	private int getDepositSum(Integer depositAmount, User user) {
		return user.getDeposit() + depositAmount;
	}

	private void validateCoinAmount(Integer deposit) {
		if (!CoinAmountUtil.isValidCoinAmount(deposit)) throw new InvalidAmountException(deposit);
	}

	private void validateProductAmount(Integer amountOfProducts, Product product) {
		if (product.getAmountAvailable() < amountOfProducts) throw new ProductAmountException();
	}

	private void validateFunds(Integer amountOfProducts, User user, Product product) {
		if (user.getDeposit() < (getTotalSpent(amountOfProducts, product))) throw new UserAmountException();
	}

	private User getUserByUsername(String username) {
		return userRepository.findOneByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}

	private Product getProductById(Long productId) {
		return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
	}

}