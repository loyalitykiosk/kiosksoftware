package com.loyalty.exception;

import org.apache.http.client.HttpResponseException;

public class KioskException extends HttpResponseException {

    public KioskException(int statusCode, String s) {
        super(statusCode, s);
    }
}
