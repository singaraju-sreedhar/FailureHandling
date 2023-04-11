package com.sre.retrytimeout.paymentservice.service;

import com.sre.retrytimeout.paymentservice.config.FallbackMethods;
import com.sre.retrytimeout.paymentservice.exceptions.ApiResponseException;
import com.sre.retrytimeout.paymentservice.exceptions.FallBackException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
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


    @Retry(name=PAYMENT_SERVICE,fallbackMethod = "fallbackMethod")
    public String RetryWithCE() throws FallBackException
    {
        log.info("Processing RetryWithCE");
        String result;
        try {
            result=restTemplate.getForObject(bankingServiceURL, String.class);
        }
        catch (HttpStatusCodeException | ResourceAccessException ex) {

            ApiResponseException apiResponseException=
                        new ApiResponseException("Custom exceptions state Object",ex);

            throw new FallBackException(apiResponseException, ex);
        }
        return result;
    }

    String fallbackMethod(FallBackException fbEx) throws ApiResponseException
    {
        log.info("fallback method of RetryWithCE ");

        log.info("Custom exceptions fallback handler handled with exception state :  "
                +fbEx.getApiResponseException().getExpDataObject().toString()
                +" Exception reason : "
                +fbEx.getOriginalException().getMessage());

        throw fbEx.getApiResponseException();
    }

}
