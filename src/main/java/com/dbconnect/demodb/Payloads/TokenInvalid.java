package com.dbconnect.demodb.Payloads;

import java.util.Date;

public class TokenInvalid {

    private String message;
    private Date timestamp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public TokenInvalid(String message) {
        this.message = message;
        this.timestamp = new Date();
    }
}
