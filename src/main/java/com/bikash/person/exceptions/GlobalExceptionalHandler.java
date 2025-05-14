package com.bikash.person.exceptions;

import com.bikash.person.globalresponse.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionalHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?>handleResourceNotFoundException( ResourceNotFoundException exception)
    {
        return  new RestResponse<>().errorWithCode(exception.getMessage(),HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public  ResponseEntity<?>handleDatabaseOperationExceptions( DatabaseOperationException exception)
    {
        return  new RestResponse<>().error(null,exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    @ExceptionHandler(IncorrectSqlException.class)
    public  ResponseEntity<?>handleIncorrectSqlExceptions( IncorrectSqlException exception)
    {
        return  new RestResponse<>().errorWithCode(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<?> handleValidationException(MethodArgumentNotValidException exception)
    {
        Map<String,String> errorData = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error)->
                {
                   String fieldName = ((FieldError)error).getField();
                   String errorMessage = error.getDefaultMessage();
                   errorData.put(fieldName,errorMessage);
                }
        );

        return  new RestResponse<>().validationError(errorData,"Validation exception",HttpStatus.BAD_REQUEST.value());
    }


    @ExceptionHandler(UnauthorizedException.class)
    public  ResponseEntity<?>handleUnauthorizedException( UnauthorizedException exception)
    {
        return  new RestResponse<>().unauthorizedError(exception.getMessage(),HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(BadRequestException.class)
    public  ResponseEntity<?>handleBadRequestException( BadRequestException exception)
    {
        return  new RestResponse<>().badRequestError(exception.getMessage(),HttpStatus.BAD_REQUEST.value());
    }

}
