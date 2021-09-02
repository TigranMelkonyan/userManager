package com.iguan.demo.usermanager.controller;

import com.iguan.demo.usermanager.exceptions.NoPermissionException;
import com.iguan.demo.usermanager.exceptions.RecordConflictException;
import com.iguan.demo.usermanager.exceptions.error.ErrorCode;
import com.iguan.demo.usermanager.model.rest.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Tigran Melkonyan
 * Date: 31/08/2021
 * Time: 21:16
 */
public abstract class AbstractController {

    private final Validator validator;

    protected AbstractController(final Validator validator) {
        this.validator = validator;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    protected final ErrorResponse returnBadRequestHttpStatus(final IllegalArgumentException e) {
        return new ErrorResponse(ErrorCode.ILLEGAL_ARGUMENT, e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RecordConflictException.class)
    @ResponseBody
    protected final ErrorResponse returnBadRequestHttpStatus(final RecordConflictException e) {
        return new ErrorResponse(e.getErrorCode(), e.getMessage());
    }

    protected final void validator(final Object request) {
        Assert.notNull(request, "request.object.is.required");
        final Set<ConstraintViolation<Object>> violations = this.validator.validate(request);
        if (!violations.isEmpty()) {
            final StringBuilder sb = new StringBuilder();
            for (final ConstraintViolation<Object> violation : violations) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(violation.getPropertyPath()).append(' ').append(violation.getMessage());
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }

    protected <T> ResponseEntity<T> respondCreated(final T object) {
        return respond(object, HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<T> respondOK(final T object) {
        return respond(object, HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> respondEmpty() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private <T> ResponseEntity<T> respond(final T object, final HttpStatus httpStatus) {
        return object == null ? respondEmpty() : new ResponseEntity<>(object, httpStatus);
    }

    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return Optional.of("UNKNOWN");
        }
        if ( !authentication.isAuthenticated()) {
            throw new NoPermissionException("AUTH ERROR", ErrorCode.NO_PERMISSION);
        }
        if(authentication.getPrincipal() instanceof String) {
            return Optional.of((String) authentication.getPrincipal());
        }
        return Optional.of(((User)authentication.getPrincipal()).getUsername());
    }
}
