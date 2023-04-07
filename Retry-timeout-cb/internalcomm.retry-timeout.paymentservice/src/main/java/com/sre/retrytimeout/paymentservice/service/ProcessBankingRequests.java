package com.sre.retrytimeout.paymentservice.service;

import com.sre.retrytimeout.paymentservice.config.FallbackMethods;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

@Service
@Slf4j
public class ProcessBankingRequests  {

    RestTemplate restTemplate = new RestTemplate();
    final String PAYMENT_SERVICE = "PaymentService";

    final String bankingServiceURL = "http://localhost:10181/getbalance";

    Function<Throwable, String> fallbackFunction;

    ProcessBankingRequests(FallbackMethods fallbackMethods) {
        fallbackFunction = fallbackMethods.fallbackFunction;
    }

    @Retry(name=PAYMENT_SERVICE,fallbackMethod = "fallbackMethod")
    public String GlobalRetry() {
        log.info("Global Retry Service calling Banking API executing");
        return restTemplate.getForObject(bankingServiceURL, String.class);
    }

    String fallbackMethod(Throwable t)
    {
        return fallbackFunction.apply(t);
    }

}
