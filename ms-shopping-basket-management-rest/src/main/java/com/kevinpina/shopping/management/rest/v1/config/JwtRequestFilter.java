package com.kevinpina.shopping.management.rest.v1.config;

import com.kevinpina.shopping.management.domain.exception.InvalidTokenException;
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

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

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
