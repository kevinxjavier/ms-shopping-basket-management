package com.kevinpina.shopping.management.domain.exception;

/**
 * Token invalid exception.
 */
public class InvalidTokenException extends RuntimeException {

	private static final long serialVersionUID = -7543480012712483523L;

	/**
	 * Constructor.
	 *
	 * @param errorMessage error message
	 */
	public InvalidTokenException(final String errorMessage) {
		super(errorMessage);
	}

}
