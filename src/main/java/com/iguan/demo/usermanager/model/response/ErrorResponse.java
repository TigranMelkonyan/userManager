package com.iguan.demo.usermanager.model.response;

import com.iguan.demo.usermanager.exceptions.error.ErrorCode;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 19:48
 */
public class ErrorResponse {
    private ErrorCode code;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorCode codes, String message) {
        this.code = codes;
        this.message = message;
    }

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
