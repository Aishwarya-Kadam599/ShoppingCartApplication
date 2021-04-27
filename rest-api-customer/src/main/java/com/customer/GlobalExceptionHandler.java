package com.customer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.customer.exceptions.UnableToStoreException;
import com.customer.exceptions.IncorrectPaswordException;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	  
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     @Override
  	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
  			HttpHeaders headers, HttpStatus status, WebRequest request) {
         Map<String, String> errors = new HashMap<>();
         ex.getBindingResult().getAllErrors().forEach((error) -> {
             String fieldName = ((FieldError) error).getField();
             String errorMessage = error.getDefaultMessage();
             errors.put(fieldName, errorMessage);
         });
         
         
       return new ResponseEntity<>(errors, headers, status);
     }
     
     @ExceptionHandler(NullPointerException.class)
     public ResponseEntity<Object> MainException()
     {
    	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
     }
     
     @ExceptionHandler(IncorrectPaswordException.class)
     public ResponseEntity<Object> IncorrectPaswordException()
     {
    	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
     }
     
     @ExceptionHandler(UnableToStoreException.class)
     public ResponseEntity<Object> UnableToStoreException()
     {
    	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
     }
     
}
