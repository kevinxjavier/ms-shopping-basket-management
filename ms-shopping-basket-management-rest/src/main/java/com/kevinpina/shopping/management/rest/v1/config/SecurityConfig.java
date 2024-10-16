package com.kevinpina.shopping.management.rest.v1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
/*		http.csrf(AbstractHttpConfigurer::disable) // csrf -> csrf.disable()
				.authorizeHttpRequests(auth -> auth
						.requestMatchers( "/public/**", "/health").permitAll()  // Allowed paths
						.requestMatchers(HttpMethod.POST, "/api/**").permitAll()  // Allowed paths
						.anyRequest().authenticated()  // Other authentication required
				).csrf(csrf -> csrf
						.ignoringRequestMatchers("/**"))
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);  // JWT filter apply
*/
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/**").permitAll()
		).csrf(csrf -> csrf
				.ignoringRequestMatchers("/**"));
		return http.build();
	}

}

