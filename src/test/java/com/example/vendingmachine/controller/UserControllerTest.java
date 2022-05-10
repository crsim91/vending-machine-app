package com.example.vendingmachine.controller;

import com.example.vendingmachine.dto.DeleteResponseDTO;
import com.example.vendingmachine.dto.ResponseUserDTO;
import com.example.vendingmachine.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest {

	public static final Long USER_ID = 1L;
	public static final String USER_USERNAME = "cristian_buyer";
	public static final HashSet<String> USER_ROLE = new HashSet<>(Collections.singleton("ROLE_BUYER"));
	public static final int USER_DEPOSIT = 100;
	public static final HashSet<GrantedAuthority> EMPTY_ROLE = new HashSet<>();
	public static final String USER_DELETE_RESPONSE = "Successfully deleted";

	private final ObjectMapper objectMapper = new ObjectMapper();
	private MockMvc mvc;

	@Mock
	UserServiceImpl userService;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	void setUp() {
		this.mvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void createUser() {
	}

	@Test
	void updateUser() {
	}

	@Test
	void testGetUserByNo_200OK() throws Exception {
		when(userService.getUser(anyLong())).thenReturn(getResponseUserDTO());
		String responseUserDTOString = objectMapper.writeValueAsString(getResponseUserDTO());

		mvc.perform(get("/api/v1/users/1"))
				.andExpect(status().is2xxSuccessful())
				.andDo(print())
				.andExpect(content().string(responseUserDTOString));
	}

	@Test
	void getUsers_expectResponseUserDTOList() {
		List<ResponseUserDTO> usersLists = new ArrayList<>();
		usersLists.add(getResponseUserDTO());

		when(userService.getUsers()).thenReturn(usersLists);
		List<ResponseUserDTO> users = userService.getUsers();

		assertNotNull(usersLists);
		assertTrue(users.size() == 1);

		verify(userService, times(1)).getUsers();
	}

	@Test
	void getUserById_expectResponseUserDTO() {
		when(userService.getUser(USER_ID)).thenReturn(getResponseUserDTO());
		ResponseUserDTO user = userService.getUser(USER_ID);

		assertNotNull(user.getId());
		assertEquals(USER_ID, user.getId());
		assertEquals(USER_USERNAME, user.getUsername());
		assertEquals(USER_DEPOSIT, user.getDeposit());
		assertEquals(USER_ROLE, user.getRoles());

		verify(userService, times(1)).getUser(USER_ID);
	}

	@Test
	void deleteUserById_expectDeleteResponse() {
		when(userService.deleteUser(USER_ID)).thenReturn(getDeleteResponse());
		DeleteResponseDTO deleteResponseDTO = userService.deleteUser(USER_ID);

		assertEquals(USER_DELETE_RESPONSE, deleteResponseDTO.getDescription());
		verify(userService, times(1)).deleteUser(USER_ID);
	}

	private DeleteResponseDTO getDeleteResponse() {
		return DeleteResponseDTO
				.builder()
				.description(USER_DELETE_RESPONSE)
				.build();
	}

	private ResponseUserDTO getResponseUserDTO() {
		return ResponseUserDTO.builder()
				.id(USER_ID)
				.username(USER_USERNAME)
				.roles(USER_ROLE)
				.deposit(USER_DEPOSIT)
				.build();
	}

}