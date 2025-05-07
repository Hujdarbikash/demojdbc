package com.bikash.person.globalresponse;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.PublicKey;
import java.time.LocalDateTime;

public class RestResponse<T> {
    private Long code;
    private LocalDateTime timestamp;
    private T data;
    private String message;




    public long getCode() {
        return code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }


    public  RestResponse()
    {

    }
    public RestResponse(long code, LocalDateTime timestamp, T data, String message) {

        this.code = code;
        this.timestamp = timestamp;
        this.data = data;
        this.message = message;
    }

    public ResponseEntity<RestResponse> okWithPayload(T data, String message) {
        return new ResponseEntity<>(new RestResponse<>(HttpStatus.OK.value(), LocalDateTime.now(), data, message), HttpStatus.OK);

    }

    public ResponseEntity<?> createdWithPayload(T data, String message) {
        return new ResponseEntity<>(new RestResponse<>(HttpStatus.CREATED.value(), LocalDateTime.now(), data, message), HttpStatus.CREATED);
    }


    public ResponseEntity<?> error(T errorData, String message, long errorCode) {
        return new ResponseEntity<>(new RestResponse<>(errorCode, LocalDateTime.now(), errorData, message), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<?> validationError(T errorData, String message, long errorCode) {
        return new ResponseEntity<>(new RestResponse<>(errorCode, LocalDateTime.now(), errorData, message), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> errorWithCode(String message, long errorCode) {
        return new ResponseEntity<>(new RestResponse<>(errorCode, LocalDateTime.now(), data, message), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> unauthorizedError(String message, long errorCode) {
        return new ResponseEntity<>(new RestResponse<>(errorCode, LocalDateTime.now(), data, message), HttpStatus.UNAUTHORIZED);
    }

}
