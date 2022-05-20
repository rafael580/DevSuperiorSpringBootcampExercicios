package com.ExercicioClientBootcampPrimeiro.controller.exception;

import com.ExercicioClientBootcampPrimeiro.service.exception.ControllerExceptionNotExist;
import com.ExercicioClientBootcampPrimeiro.service.exception.DataBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ControllerExceptionNotExist .class)
    public ResponseEntity<StandardError> entityNotFound(ControllerExceptionNotExist e , HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Resource not found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> DataIntegrety(DataBaseException e , HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Integrety Data");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
