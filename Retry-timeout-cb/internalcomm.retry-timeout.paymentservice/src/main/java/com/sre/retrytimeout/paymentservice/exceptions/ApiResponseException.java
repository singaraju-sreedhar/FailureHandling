package com.sre.retrytimeout.paymentservice.exceptions;


public class ApiResponseException extends RuntimeException {
    Object expDataObject;
    Exception originalException;
    public  ApiResponseException(Object expData, Exception ex)
    {
        expDataObject=expData;
        originalException=ex;
    }

    public  Object getExpDataObject()
    {
        return  expDataObject;
    }

    public Exception getOriginalException()
    {
        return originalException;
    }
}
