package com.kevinpina.shopping.management.rest.v1.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Value("${security.jwt.secret-key}")
	private String secretKey;

	/*
	private final RequestMatcher ignoredPaths = new AntPathRequestMatcher("/api/v1/shopping_management/csv/upload");

	private final List<AntPathRequestMatcher> excludedMatchers;

	public JwtRequestFilter (List<AntPathRequestMatcher> excludedMatchers) {
		excludedMatchers = new ArrayList<>();
		excludedMatchers.add(new AntPathRequestMatcher("/api/v1/shopping_management/csv/upload"));
		this.excludedMatchers = excludedMatchers;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return excludedMatchers.stream()
				.anyMatch(matcher -> matcher.matches(request));
	}
	*/

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		/*if (this.ignoredPaths.matches(request)) {
			filterChain.doFilter(request, response);
			return;
		}*/

		String authorizationHeader = request.getHeader("Authorization".toLowerCase());
		String username = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			try {
				Claims claims = Jwts.parser()
						.setSigningKey(secretKey)
						.parseClaimsJws(token)
						.getBody();
				username = claims.getSubject();
				request.setAttribute("username", username);
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		filterChain.doFilter(request, response);
	}

}