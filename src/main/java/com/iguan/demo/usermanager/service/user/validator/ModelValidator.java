package com.iguan.demo.usermanager.service.user.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created by Tigran Melkonyan
 * Date: 30/08/2021
 * Time: 21:24
 */
@Component
public class ModelValidator {

    private final Validator validator;

    public ModelValidator(final Validator validator) {
        this.validator = validator;
    }

    public void validate(final Object request) {
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
}
