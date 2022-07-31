package com.example.day19assignment.aop;

import com.example.day19assignment.domain.response.ErrorResponse;
import com.example.day19assignment.exception.ExistingUserException;
import com.example.day19assignment.exception.UserCreationException;
import com.example.day19assignment.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e){
        return new ResponseEntity<>(ErrorResponse.builder().message("User not found").build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {ExistingUserException.class})
    public ResponseEntity<ErrorResponse> handleExistingUserFoundException(ExistingUserException e){
        return new ResponseEntity<>(ErrorResponse.builder().message("Username or email is already taken").build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {UserCreationException.class})
    public ResponseEntity<ErrorResponse> handleUserCreationError(UserCreationException e){
        return new ResponseEntity<>(ErrorResponse.builder().message("User Creation Failed - provide all information").build(), HttpStatus.OK);
    }
}
