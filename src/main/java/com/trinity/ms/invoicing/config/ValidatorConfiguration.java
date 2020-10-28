package com.trinity.ms.invoicing.config;

import com.trinity.commons.Validator;
import com.trinity.ms.invoicing.sale_session.constraints.SessionConstraint;
import com.trinity.ms.invoicing.sale_session.constraints.UserRoleConstraint;
import com.trinity.ms.invoicing.sale_session.repository.SaleSessionRepository;
import com.trinity.ms.invoicing.share.proxy.LoginProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ValidatorConfiguration {

    @Autowired
    private LoginProxy loginProxy;

    @Bean
    public List<Validator<Integer>> userValidators(SaleSessionRepository saleSessionRepository) {
        List<Validator<Integer>> validators = new ArrayList<>();
        validators.add(new SessionConstraint(saleSessionRepository));
        return validators;
    }

    @Bean
    public List<Validator<String>> authorizedUserValidators() {
        List<Validator<String>> validators = new ArrayList<>();
        validators.add(new UserRoleConstraint(loginProxy));
        return validators;
    }

}
