package com.sre.retrytimeout.paymentservice.exceptions;

public class FallBackException extends  RuntimeException{
    ApiResponseException apiResponseException;

    public FallBackException(ApiResponseException ex)
    {
        apiResponseException=ex;
    }

    public ApiResponseException getApiResponseException() {
        return apiResponseException;
    }
}
