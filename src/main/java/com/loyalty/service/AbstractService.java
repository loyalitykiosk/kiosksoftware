package com.loyalty.service;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by kabachko on 6/26/2016.
 */
public  abstract class AbstractService implements KioskService {

    private HttpClient httpClient;
    private ObjectMapper mapper;

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

}
