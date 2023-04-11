package com.sre.retrytimeout.paymentservice.exceptions;


public class CustomException extends RuntimeException {
    Object expDataObject;
    public  CustomException(Object expData)
    {
        expDataObject=expData;
    }

    public  Object getExpDataObject()
    {
        return  expDataObject;
    }

}
