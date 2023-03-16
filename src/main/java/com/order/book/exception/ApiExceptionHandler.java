package com.order.book.exception;

import com.order.book.dto.Response;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler( value = {ApiRequestException.class} )
    public final ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        Response responseEx = Response.builder()
                .status(badRequest)
                .statusCode(badRequest.value())
                .message( e.getMessage() )
                .timeStamp( LocalDateTime.now() )
                .build();

        log.error (  e.getLocalizedMessage() );

        return new ResponseEntity<>(responseEx,
                     badRequest);
    }

    @ExceptionHandler ( Exception.class )
    public final ResponseEntity<Object> handleAllExceptions( Exception e )
    {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        Response responseEx = Response.builder()
                .status(badRequest)
                .statusCode(badRequest.value())
                .message( e.getMessage() )
                .timeStamp( LocalDateTime.now() )
                .build();

        log.error (  e.getLocalizedMessage() );
        
        return new ResponseEntity<>(responseEx,
                badRequest);
    }

}
