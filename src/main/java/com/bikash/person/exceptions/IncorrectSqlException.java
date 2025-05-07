package com.bikash.person.exceptions;

import lombok.Data;

@Data
public class IncorrectSqlException extends  RuntimeException {
    public IncorrectSqlException(String  message)
    {
        super(message);
    }
}
