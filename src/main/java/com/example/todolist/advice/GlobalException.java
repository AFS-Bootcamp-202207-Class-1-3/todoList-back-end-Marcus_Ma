package com.example.todolist.advice;

import com.example.todolist.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException  {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorRespone handleNotFoundException(Exception exception){
        return new ErrorRespone(exception.getMessage(), HttpStatus.NOT_FOUND.value());
    }

}
