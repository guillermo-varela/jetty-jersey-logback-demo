package com.blogspot.nombre_temp.jetty.jersey.logback.demo.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class DemoResponse {

    private Date date;
    private boolean error;
    private String message;

    public DemoResponse() {
        date = new Date();
    }

    public DemoResponse(boolean error) {
        this();
        this.error = error;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
