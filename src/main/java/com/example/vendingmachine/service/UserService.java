package com.example.vendingmachine.service;

import com.example.vendingmachine.dto.DeleteResponseDTO;
import com.example.vendingmachine.dto.ResponseUserDTO;
import com.example.vendingmachine.dto.UserDTO;
import com.example.vendingmachine.model.User;

import java.util.List;

public interface UserService {

	User createUser(UserDTO user);

	ResponseUserDTO getUser(Long userId);

	List<ResponseUserDTO> getUsers();

	User updateUser(Long userId, UserDTO user);

	DeleteResponseDTO deleteUser(Long id);

}