package com.mentalhealthapi.exceptions;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<String> handleTokenExpiredException(TokenExpiredException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
	
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException ex) {
        String errorMessage = "JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.";
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<String> handleJwtAuthenticationException(JwtAuthenticationException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

