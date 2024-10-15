package com.kevinpina.shopping.management.domain.exception;

/**
 * Token expired exception.
 */
public class TokenExpiredException extends RuntimeException {

	private static final long serialVersionUID = -7543480012712483523L;

	/**
	 * Constructor.
	 *
	 * @param errorMessage error message
	 */
	public TokenExpiredException(final String errorMessage) {
		super(errorMessage);
	}

}