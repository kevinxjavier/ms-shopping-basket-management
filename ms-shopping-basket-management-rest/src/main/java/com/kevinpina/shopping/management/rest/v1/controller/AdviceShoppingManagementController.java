package com.kevinpina.shopping.management.rest.v1.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.kevinpina.shopping.management.domain.exception.FailParsingCSVFileException;
import com.kevinpina.shopping.management.domain.exception.InvalidTokenException;
import com.kevinpina.shopping.management.domain.exception.TokenExpiredException;
import com.kevinpina.shopping.management.rest.v1.client.model.ErrorDTO;
import com.kevinpina.shopping.management.rest.v1.client.model.ErrorsDTO;
import jakarta.validation.ConstraintViolationException;
import jakarta.xml.bind.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class Advice controller.
 */
@ControllerAdvice(basePackages = "com.kevinpina.shopping.management.rest.v1.controller")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AdviceShoppingManagementController extends ResponseEntityExceptionHandler {

	private static final String UNEXPECTED_SERVER_ERROR = "Unexpected server error";
	private static final Logger log = LoggerFactory.getLogger(AdviceShoppingManagementController.class);

	/**
	 * Handle Method Argument NotValid.
	 *
	 * @param ex      ex
	 * @param headers headers
	 * @param status  status
	 * @param request request
	 * @return response
	 */
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
	                                                           final HttpHeaders headers,
	                                                           final HttpStatusCode status,
	                                                           final WebRequest request) {
		final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		final List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
		final List<String> errors = new ArrayList<>();

		for (final FieldError fieldError : fieldErrors) {
			errors.add(fieldError.getField() + ", " + fieldError.getDefaultMessage());
		}

		for (final ObjectError objectError : globalErrors) {
			errors.add(objectError.getObjectName() + ", " + objectError.getDefaultMessage());
		}

		log.error("handleMethodArgumentNotValid", ex);
		return this.error(HttpStatus.BAD_REQUEST, Integer.toString(HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), errors.toString());
	}

	/**
	 * Handle HttpMessage Not Readable.
	 *
	 * @param ex      ex
	 * @param headers headers
	 * @param status  status
	 * @param request request
	 * @return response
	 */
	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
	                                                           final HttpHeaders headers, final HttpStatusCode status,
	                                                           final WebRequest request) {
		log.error("handleHttpMessageNotReadable", ex);
		final Throwable mostSpecificCause = ex.getMostSpecificCause();
		String message;
		if (mostSpecificCause instanceof JsonParseException jsonParseException) {
			message = jsonParseException.getOriginalMessage();
		} else if (mostSpecificCause instanceof MismatchedInputException mismatchedInputException) {
			message = mismatchedInputException.getOriginalMessage();
		} else {
			message = mostSpecificCause.getMessage();
		}
		return this.error(HttpStatus.BAD_REQUEST, Integer.toString(HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), message);
	}

	/**
	 * Handle Http Media Type NotSupported.
	 *
	 * @param ex      ex
	 * @param headers header
	 * @param status  status
	 * @param request request
	 * @return response
	 */
	@Override
	public ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
	                                                              final HttpHeaders headers,
	                                                              final HttpStatusCode status,
	                                                              final WebRequest request) {
		log.error("handleHttpMediaTypeNotSupported", ex);
		return this.error(HttpStatus.BAD_REQUEST, Integer.toString(HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), ex.getMessage());
	}

	/**
	 * Handle ServletRequest BindingException.
	 *
	 * @param ex      ex
	 * @param headers headers
	 * @param status  status
	 * @param request request
	 * @return response
	 */
	@Override
	public ResponseEntity<Object> handleServletRequestBindingException(final ServletRequestBindingException ex,
	                                                                   final HttpHeaders headers,
	                                                                   final HttpStatusCode status,
	                                                                   final WebRequest request) {
		log.error("handleServletRequestBindingException", ex);
		return this.error(HttpStatus.BAD_REQUEST, Integer.toString(HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), ex.getMessage());
	}

	/**
	 * Handle HttpRequest Method NotSupported.
	 *
	 * @param ex      ex
	 * @param headers headers
	 * @param status  status
	 * @param request request
	 * @return response
	 */
	@Override
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
	                                                                  final HttpHeaders headers,
	                                                                  final HttpStatusCode status,
	                                                                  final WebRequest request) {
		log.error("handleHttpRequestMethodNotSupported", ex);
		return this.error(HttpStatus.METHOD_NOT_ALLOWED, Integer.toString(HttpStatus.METHOD_NOT_ALLOWED.value()),
				HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), ex.getMessage());
	}

	/**
	 * Handle unexpected exception response entity.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleUnexpectedException(final Exception ex) {
		log.error("handleUnexpectedException", ex);
		return this.error(HttpStatus.INTERNAL_SERVER_ERROR, Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ErrorDTO.LevelEnum.FATAL.name(), UNEXPECTED_SERVER_ERROR);
	}

	/**
	 * Handle validation exception response entity.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<Object> handleValidationException(final ValidationException ex) {
		log.error("handleValidationException", ex);
		return this.error(HttpStatus.BAD_REQUEST, Integer.toString(HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), ex.getMessage());
	}

	/**
	 * Handle illegal argument exception response entity.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex) {
		log.error("handleIllegalArgumentException", ex);
		return this.error(HttpStatus.BAD_REQUEST, Integer.toString(HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), ex.getMessage());
	}

	/**
	 * Handle Access Denied exception response entity.
	 *
	 * @param e the ex
	 * @return the response entity
	 */
	@ExceptionHandler(value = AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException e) {
		log.error("handleAccessDeniedException", e);
		return this.error(HttpStatus.FORBIDDEN, Integer.toString(HttpStatus.FORBIDDEN.value()),
				HttpStatus.FORBIDDEN.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), e.getMessage());
	}

	/**
	 * ConstraintViolationException.
	 *
	 * @param ex exception
	 * @return response entity
	 */
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex) {
		log.error("handleConstraintViolationException", ex);
		return this.error(HttpStatus.BAD_REQUEST, Integer.toString(HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), ex.getMessage());
	}

	/**
	 * Handle TokenExpiredException.
	 *
	 * @param e ex
	 * @return response
	 */
	@ExceptionHandler(value = TokenExpiredException.class)
	public ResponseEntity<Object> handleTokenExpiredException(final TokenExpiredException e) {
		return buildResponseEntityError(HttpStatus.UNAUTHORIZED, Integer.toString(HttpStatus.UNAUTHORIZED.value()),
				HttpStatus.UNAUTHORIZED.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), e);
	}

	/**
	 * Handle FailParsingCSVFileException.
	 *
	 * @param e ex
	 * @return response
	 */
	@ExceptionHandler(value = FailParsingCSVFileException.class)
	public ResponseEntity<Object> handleFailParsingCSVFileException(final FailParsingCSVFileException e) {
		return buildResponseEntityError(HttpStatus.BAD_REQUEST, Integer.toString(HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), e);
	}

	/**
	 * Handle InvalidTokenException.
	 *
	 * @param e ex
	 * @return response
	 */
	@ExceptionHandler(value = InvalidTokenException.class)
	public ResponseEntity<Object> handleInvalidTokenException(final InvalidTokenException e) {
		return buildResponseEntityError(HttpStatus.FORBIDDEN, Integer.toString(HttpStatus.FORBIDDEN.value()),
				HttpStatus.FORBIDDEN.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), e);
	}

	private ResponseEntity<Object> error(final HttpStatus httpStatus, final String code, final String message,
	                                     final String level, final String descriptions) {
		final List<ErrorDTO> errors = new ArrayList<>();
		final ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.code(code);
		errorDTO.description(descriptions);
		errorDTO.message(message);
		errorDTO.level(ErrorDTO.LevelEnum.valueOf(level));
		errors.add(errorDTO);

		final ErrorsDTO gtsErrorDTO = new ErrorsDTO();
		gtsErrorDTO.errors(errors);
		return new ResponseEntity<>(gtsErrorDTO, httpStatus);
	}


	private ResponseEntity<Object> buildResponseEntityError(final HttpStatus httpStatus, final String value, final String reason,
	                                                        final String errorLevel, final RuntimeException ex) {
		log.error("buildResponseEntityError", ex);
		return this.error(httpStatus, value, reason, errorLevel, ex.getMessage());
	}


}
