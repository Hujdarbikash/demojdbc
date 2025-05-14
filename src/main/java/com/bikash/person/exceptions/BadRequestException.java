package com.bikash.person.exceptions;

public class BadRequestException extends  RuntimeException{
    public  BadRequestException(String message)
    {
        super(message);
    }
}
