package com.iguan.demo.usermanager.exceptions;

import com.iguan.demo.usermanager.exceptions.error.ErrorCode;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 18:32
 */
public class RecordConflictException extends RuntimeException {
    
    private final ErrorCode errorCode;

    public RecordConflictException(
            final String message,
            final ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
