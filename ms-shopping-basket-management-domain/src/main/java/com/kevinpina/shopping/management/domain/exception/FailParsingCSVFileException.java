package com.kevinpina.shopping.management.domain.exception;

/**
 * CSV file parsing exception.
 */
public class FailParsingCSVFileException extends RuntimeException {

	private static final long serialVersionUID = -4263480012712483584L;

	/**
	 * Constructor.
	 *
	 * @param errorMessage error message
	 */
	public FailParsingCSVFileException(final String errorMessage) {
		super(errorMessage);
	}

}
