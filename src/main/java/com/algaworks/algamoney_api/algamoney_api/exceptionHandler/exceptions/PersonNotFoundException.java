package com.algaworks.algamoney_api.algamoney_api.exceptionHandler.exceptions;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(String msg){
        super(msg);
    }
}
