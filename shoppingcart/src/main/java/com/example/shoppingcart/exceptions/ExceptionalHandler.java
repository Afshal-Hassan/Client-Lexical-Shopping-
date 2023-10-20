package com.example.shoppingcart.exceptions;

import com.example.shoppingcart.pojos.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
@RestController
public class ExceptionalHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class) //this will handle all types of exception
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        ex.printStackTrace();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }



    @ExceptionHandler(CustomNotFoundException.class) //this will handle only UserNotFoundException(user defined)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        ex.printStackTrace();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(CustomBadRequestException.class) //this will handle only UserNotFoundException(user defined)
    public final ResponseEntity<Object> handleCustomBadRequestException(Exception ex, WebRequest request) {
        System.out.println("YES");
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        ex.printStackTrace();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }
}
