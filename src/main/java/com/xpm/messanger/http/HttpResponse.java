package com.xpm.messanger.http;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Setter
@Getter
public class HttpResponse {
    private HttpStatus httpStatus = HttpStatus.OK;
    private int statusCode;
    private String statusMessage;
    private Object content = null;
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public HttpResponse(HttpStatus httpStatus, String statusMessage, Object content) {
        this.httpStatus = httpStatus;
        this.statusMessage = statusMessage;
        this.content = content;
        this.statusCode = this.httpStatus.value();
    }

    public HttpResponse(HttpStatus httpStatus, String statusMessage) {
        this.httpStatus = httpStatus;
        this.statusMessage = statusMessage;
        this.statusCode = this.httpStatus.value();
    }

    public HttpResponse(String statusMessage) {
        this.statusMessage = statusMessage;
        this.statusCode = this.httpStatus.value();
    }

    public HttpResponse(String statusMessage, Object content) {
        this.statusMessage = statusMessage;
        this.content = content;
        this.statusCode = this.httpStatus.value();
    }

}
