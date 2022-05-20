
package com.ExercicioClientBootcampPrimeiro.service.exception;

public class ControllerExceptionNotExist extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ControllerExceptionNotExist(String msg){
        super(msg);
    }
}
