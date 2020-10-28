package com.trinity.ms.invoicing.sale_session.constraints;

import com.trinity.commons.Validator;
import com.trinity.ms.invoicing.sale_session.exception.PermissionDeniedException;
import com.trinity.ms.invoicing.sale_session.exception.SessionAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ConstraintComponent {

    private List<Validator<Integer>> userValidators;

    private List<Validator<String>> authorizedUserValidators;

    public void validateAuthorizedUserConstraint(String supervisorCredentials) {
        authorizedUserValidators
            .stream()
                .filter(validator -> !validator.validate(supervisorCredentials))
                .forEach(validator -> {
                    throw new PermissionDeniedException(validator.getMessage());
                });
    }

     public void validateByConstraintsClass(String userId, Class... classes) {
        Arrays.stream(classes).forEach(validator -> System.out.println(validator.getCanonicalName()));
        authorizedUserValidators
            .stream()
                .filter(validator -> validator instanceof UserRoleConstraint)
                .filter(validator -> !validator.validate(userId))
                .forEach(validator -> {
                    throw new PermissionDeniedException(validator.getMessage());
                });
    }

    public void validateUserSessionConstraint(Integer userId) {
        userValidators
            .stream()
                .filter(validator -> !validator.validate(userId))
                .forEach(validator -> {
                    throw new SessionAlreadyExistsException(validator.getMessage());
                });
    }

    @Autowired
    @Qualifier("userValidators")
    public void setUserValidators(List<Validator<Integer>> userValidators) {
        this.userValidators = userValidators;
    }

    @Autowired
    @Qualifier("authorizedUserValidators")
    public void setAuthorizedUserValidators(List<Validator<String>> authorizedUserValidators) {
        this.authorizedUserValidators = authorizedUserValidators;
    }

}
