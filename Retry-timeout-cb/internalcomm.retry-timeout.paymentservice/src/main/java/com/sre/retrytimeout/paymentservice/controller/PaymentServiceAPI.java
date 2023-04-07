package com.sre.retrytimeout.paymentservice.controller;


import com.sre.retrytimeout.paymentservice.service.ProcessBankingRequests;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import io.github.resilience4j.retry.annotation.Retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class PaymentServiceAPI {

    @Autowired
    ProcessBankingRequests bankingRequests;

    RestTemplate restTemplate=new RestTemplate();
    final String PAYMENT_SERVICE = "PaymentService";

    final String bankingServiceURL = "http://localhost:10181/getbalance";

    @GetMapping("/getbalance")
    @Retry(name=PAYMENT_SERVICE,fallbackMethod="fallbackGetBalance")
    @CircuitBreaker(name = PAYMENT_SERVICE, fallbackMethod = "cbFallbackGetBalance")
    public ResponseEntity<String> getBalance() {

        log.info("Calling REST API getBalance of BankingService");
        String balance = "";


        try {
            balance = restTemplate.getForObject(bankingServiceURL, String.class);
        } catch (Exception ex) {
            log.error("exception caught in code");
            throw ex;
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                "PaymentService getBalance succeeded.\n" +
                        " Response from Banking Service is ::: " + balance);

    }

    public static ResponseEntity<String> cbFallbackGetBalance(CallNotPermittedException exception) {

        log.info("Circuit breaker Fallback for get balance invoked due to exception ");

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .body("FAllback Circuit breaker  handler of PaymentService getBalance. Reason : "
                        + exception.getMessage());
    }

    //fall back handler
    public static ResponseEntity<String> fallbackGetBalance(Exception exception) {

        log.info("Retry Fallback for get balance invoked due to exception ");

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .body("FAllback Retry handler of PaymentService getBalance . Reason: "
                        + exception.getMessage());
    }


    /*
        API to demonstrate global fallback handling mechanism
     */
    @GetMapping("/globalretry")
    public String GlobalRetry()
    {
        log.info("Global Retry API executing");
        return bankingRequests.GlobalRetry();
    }

}
