package com.galen.micro.zuul.api.fallback;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ApiClientHttpResponseImpl implements ClientHttpResponse {

    private String describe;

    public ApiClientHttpResponseImpl() {
        describe = "sorry! i'm the fallback";
    }

    public ApiClientHttpResponseImpl(String describe) {
        this.describe = "{\"status\":901,\"msg\":\"" + describe + " not available\",\"total\":0,\"bean\":\"null\"}";
    }

    public ApiClientHttpResponseImpl(String describe, boolean isCustom) {
        if (isCustom)
            this.describe = describe;
        else
            this.describe = "{\"status\":901,\"msg\":\"" + describe + " not available\",\"total\":0,\"bean\":\"null\"}";
    }

    @Override
    public HttpStatus getStatusCode() throws IOException {
        return HttpStatus.OK;
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return 201;
    }

    @Override
    public String getStatusText() throws IOException {
        return "not ok";
    }

    @Override
    public void close() {

    }

    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(describe.getBytes());
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON); //json格式返回
        return httpHeaders;
    }
}
