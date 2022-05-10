package com.example.vendingmachine.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtils {

	private SecurityUtils() {
	}

	public static String getContextUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = "";
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
				userName = springSecurityUser.getUsername();
			} else if (authentication.getPrincipal() instanceof String) {
				userName = (String) authentication.getPrincipal();
			}
		}
		return userName;
	}

}