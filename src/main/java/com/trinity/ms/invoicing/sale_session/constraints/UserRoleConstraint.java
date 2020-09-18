package com.trinity.ms.invoicing.sale_session.constraints;

import com.trinity.commons.Validator;

public class UserRoleConstraint implements Validator<Integer> {

    private String message;

    @Override
    public boolean validate(Integer userId) {

        message = String.format("Permission denied to [%d] user.", userId);

        return true;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
