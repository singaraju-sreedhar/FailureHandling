package com.sre.retrytimeout.paymentservice.config;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Slf4j
@Component
@ComponentScan
public class FallbackMethods {

    public Function<Throwable, String> fallbackFunction = (throwable) ->
    {
        String result="Common Retry Fallback handler handling error : "+throwable;

        log.error(result);

        /*
            Add code as per business relevance
         */

        return result;
    };

}
