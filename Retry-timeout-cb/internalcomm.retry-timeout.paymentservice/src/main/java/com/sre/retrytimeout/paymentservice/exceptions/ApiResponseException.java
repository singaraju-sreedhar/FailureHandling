package com.sre.retrytimeout.paymentservice.exceptions;


public class ApiResponseException extends RuntimeException {
    Object expDataObject;
    public  ApiResponseException(Object expData, Exception ex)
    {
        expDataObject=expData;
    }

    public  Object getExpDataObject()
    {
        return  expDataObject;
    }
}
