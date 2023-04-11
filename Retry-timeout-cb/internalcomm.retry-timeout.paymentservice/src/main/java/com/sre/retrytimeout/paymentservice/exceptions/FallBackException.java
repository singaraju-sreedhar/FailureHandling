package com.sre.retrytimeout.paymentservice.exceptions;

public class FallBackException extends  RuntimeException{
    ApiResponseException apiResponseException;
    Exception originalException;

    public FallBackException(ApiResponseException apiEx, Exception ex)
    {
        apiResponseException=apiEx;
        originalException=ex;
    }

    public ApiResponseException getApiResponseException() {
        return apiResponseException;
    }

    public Exception getOriginalException()
    {
        return originalException;
    }
}
