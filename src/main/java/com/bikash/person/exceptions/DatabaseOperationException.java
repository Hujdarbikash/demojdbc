package com.bikash.person.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DatabaseOperationException  extends  RuntimeException {
    public  DatabaseOperationException(String message)
    {
        super(message);
    }
}
