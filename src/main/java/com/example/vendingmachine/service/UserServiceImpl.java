package com.example.vendingmachine.service;

import com.example.vendingmachine.dto.DeleteResponseDTO;
import com.example.vendingmachine.dto.ResponseUserDTO;
import com.example.vendingmachine.dto.UserDTO;
import com.example.vendingmachine.exception.InvalidAmountException;
import com.example.vendingmachine.exception.ProductConflictException;
import com.example.vendingmachine.exception.UserNotFoundException;
import com.example.vendingmachine.exception.UsernameConflictException;
import com.example.vendingmachine.model.User;
import com.example.vendingmachine.repository.UserRepository;
import com.example.vendingmachine.util.CoinAmountUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public User createUser(UserDTO user) {
		validateCoinType(user.getDeposit());

		User preparedUser = User.builder()
				.username(user.getUsername())
				.password(new BCryptPasswordEncoder().encode(user.getPassword()))
				.deposit(user.getDeposit())
				.roles(new HashSet<>())
				.build();

		roleAssign(user, preparedUser);

		try {
			log.info("Creating user {}", preparedUser);
			return userRepository.save(preparedUser);
		} catch (DataIntegrityViolationException e) {
			log.error("Username duplication {}", e.getMessage());
			throw new UsernameConflictException(preparedUser.getUsername());
		}
	}

	@Override
	public ResponseUserDTO getUser(Long userId) {
		User userById = getUserById(userId);
		return ResponseUserDTO.builder()
				.id(userById.getId())
				.username(userById.getUsername())
				.deposit(userById.getDeposit())
				.roles(new HashSet<>(Collections.singleton(userById.getRoles().toString())))
				.build();
	}

	@Override
	public List<ResponseUserDTO> getUsers() {
		return getAllUsers().stream()
				.map(user -> ResponseUserDTO.builder()
						.id(user.getId())
						.username(user.getUsername())
						.deposit(user.getDeposit())
						.roles(new HashSet<>(Collections.singleton(user.getRoles().toString())))
						.build()).collect(Collectors.toList());
	}

	@Override
	public User updateUser(Long userId, UserDTO user) {
		validateCoinType(user.getDeposit());

		User preparedUser = User.builder()
				.username(user.getUsername())
				.password(new BCryptPasswordEncoder().encode(user.getPassword()))
				.deposit(user.getDeposit())
				.roles(new HashSet<>())
				.build();

		roleAssign(user, preparedUser);

		try {
			preparedUser.setId(userId);
			log.info("Update user {}", preparedUser);
			return userRepository.save(preparedUser);
		} catch (DataIntegrityViolationException e) {
			log.error("Username duplication {}", e.getMessage());
			throw new UsernameConflictException(preparedUser.getUsername());
		}
	}

	@Override
	public DeleteResponseDTO deleteUser(Long id) {
		userRepository.delete(getUserById(id));
		return new DeleteResponseDTO();
	}

	private List<User> getAllUsers() {
		return userRepository.findAll();
	}

	private User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	private void validateCoinType(Integer deposit) {
		if (!CoinAmountUtil.isValidCoinAmount(deposit)) throw new InvalidAmountException(deposit);
	}

	private void roleAssign(UserDTO user, User preparedUser) {
		user.getRoles().forEach(role -> preparedUser.getRoles().add(new SimpleGrantedAuthority(role)));
	}

}