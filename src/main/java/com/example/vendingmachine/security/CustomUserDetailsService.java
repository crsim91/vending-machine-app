package com.example.vendingmachine.security;

import com.example.vendingmachine.exception.UserNotFoundException;
import com.example.vendingmachine.model.User;
import com.example.vendingmachine.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) {
		log.debug(MessageFormat.format("Authenticating: {0}.", username));
		User user = userRepository.findOneByUsername(username).orElseThrow(UserNotFoundException::new);
		Set<GrantedAuthority> grantedAuthorities = user.getRoles();

		return new org.springframework.security.core.userdetails.User(username.toLowerCase(), user.getPassword(), grantedAuthorities);
	}

}