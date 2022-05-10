package com.example.vendingmachine.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final CustomUserDetailsService userDetailsService;
	private final TokenProvider tokenProvider;

	@PostConstruct
	public void init() {
		try {
			authenticationManagerBuilder.userDetailsService(userDetailsService);
		} catch (Exception e) {
			throw new BeanInitializationException("Security configuration failed: ", e.getCause());
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JWTFilter jwtFilter = new JWTFilter(tokenProvider);
		http
				.headers().cacheControl().disable().and()
				.csrf().disable().headers().frameOptions().disable()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()

				.antMatchers(HttpMethod.GET, "/api/v1/products").authenticated()
				.antMatchers(HttpMethod.GET, "/api/v1/products/*").authenticated()
				.antMatchers(HttpMethod.POST, "/api/v1/products").authenticated()
				.antMatchers(HttpMethod.PUT, "/api/v1/products/*").authenticated()
				.antMatchers(HttpMethod.DELETE, "/api/v1/products/*").authenticated()

				.antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/users/*").authenticated()
				.antMatchers(HttpMethod.GET, "/api/v1/users").authenticated()
				.antMatchers(HttpMethod.PUT, "/api/v1/users/*").authenticated()
				.antMatchers(HttpMethod.DELETE, "/api/v1/users/*").authenticated()

				.antMatchers(HttpMethod.DELETE, "/api/v1/actions/*").authenticated();
	}

}