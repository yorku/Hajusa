package com.yorku.Hajus.model.Controller;

import java.net.ConnectException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javassist.NotFoundException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.yorku.Hajus.model.Exception.CustomValidationException;
import com.yorku.Hajus.model.Log.MyLogger;
import com.yorku.Hajus.model.Validator.ErrorResponse;
import com.yorku.Hajus.model.Validator.FieldErrorResource;
import com.yorku.Hajus.model.Validator.SingularErrorResponse;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public RestExceptionHandler() {
        super();
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
   
    	List<FieldErrorResource> fieldErrorResources = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        
        for (FieldError fieldError : fieldErrors) {
            FieldErrorResource fieldErrorResource = new FieldErrorResource();
            fieldErrorResource.setResource(fieldError.getObjectName());
            fieldErrorResource.setField(fieldError.getField());
            fieldErrorResource.setCode(fieldError.getCode());
            fieldErrorResource.setMessage(fieldError.getDefaultMessage());
            fieldErrorResources.add(fieldErrorResource);
        }
        
        ErrorResponse error = new ErrorResponse(ServerConfig.MethodArgumentNotValidException);
        error.setFieldErrors(fieldErrorResources);

        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(ex, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
    
//    @Override
//    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//        
//    	String unsupported = "Unsupported content type: " + ex.getContentType();
//        String supported   = "Supported content types: "  + MediaType.toString(ex.getSupportedMediaTypes());
//        return handleExceptionInternal(ex, new ErrorMessage(unsupported, supported), headers, status, request);
//    }
// 
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//        
//    	Throwable mostSpecificCause = ex.getMostSpecificCause();
//        ErrorMessage errorMessage;
//        if (mostSpecificCause != null) {
//            errorMessage = new ErrorMessage(mostSpecificCause.getClass().getName(),
//            		                        mostSpecificCause.getMessage());
//        } else {
//            errorMessage = new ErrorMessage(ex.getMessage());
//        }
//        return handleExceptionInternal(ex, errorMessage, headers, status, request);
//    }
    
//
//  @Override
//  protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//      final String bodyOfResponse = "This should be application specific";
//      // ex.getCause() instanceof JsonMappingException, JsonParseException // for additional information later on
//      return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
//  }
//  
//	  @Override
//	  protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//	      final String bodyOfResponse = "This should be application specific";
//	  // ex.getCause() instanceof JsonMappingException, JsonParseException // for additional information later on
//	      return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
//	  }
    
    // 403

//    // 404
//
//    @ExceptionHandler(value = { EntityNotFoundException.class })
//    protected ResponseEntity<Object> handleBadRequest(final EntityNotFoundException ex, final WebRequest request) {
//        final String bodyOfResponse = "This should be application specific";
//        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//
//    // 409
//
//    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class })
//    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
//        final String bodyOfResponse = "This should be application specific";
//        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }
//
//    // 412
//
//    // 500
//
    @ExceptionHandler({ ConnectException.class })
    public ResponseEntity<Object> handleConnectException(final ConnectException ex, final WebRequest request) {
    	   
        ErrorResponse error = new ErrorResponse("Service unavailable!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        return handleExceptionInternal(ex, error, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    @ExceptionHandler({ SocketException.class })
    public ResponseEntity<Object> handleSocketException(final SocketException ex, final WebRequest request) {
        final String bodyOfResponse = "This should be application specific";
        MyLogger.LogMessage(bodyOfResponse);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    @ExceptionHandler({ CustomValidationException.class })
    public ResponseEntity<Object> handleCustomValidationException(final CustomValidationException ex, final WebRequest request) {

    	SingularErrorResponse errorMessage = new SingularErrorResponse(ServerConfig.CustomValidationException);
    	errorMessage.setExplanation(ex.getMessage());
    	return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(final NotFoundException ex, final WebRequest request) {
    	
    	SingularErrorResponse errorMessage = new SingularErrorResponse(ServerConfig.NotFoundException);
    	errorMessage.setExplanation(ex.getMessage());
    	return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler({ ConstraintViolationException.class, DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleSQLExceptions(final ConstraintViolationException ex, final WebRequest request) {
    	
    	SingularErrorResponse errorMessage = new SingularErrorResponse(ServerConfig.ConstraintViolationException);
    	errorMessage.setExplanation(ex.getSQLException().getMessage());
    	return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler({ CannotCreateTransactionException.class })
    public ResponseEntity<Object> handleCannotCreateTransactionException(final CannotCreateTransactionException ex, final WebRequest request) {
    	
    	SingularErrorResponse errorMessage = new SingularErrorResponse(ServerConfig.CannotCreateTransactionException);
    	errorMessage.setExplanation("Database server is down.");
    	return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}