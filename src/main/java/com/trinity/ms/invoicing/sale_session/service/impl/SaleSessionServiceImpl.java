package com.trinity.ms.invoicing.sale_session.service.impl;

import com.trinity.commons.Validator;
import com.trinity.commons.model.SaleSession;
import com.trinity.commons.payload.TokenSession;
import com.trinity.commons.security.payload.Account;
import com.trinity.ms.invoicing.sale_session.exception.PermissionDeniedException;
import com.trinity.ms.invoicing.sale_session.exception.SessionAlreadyExistsException;
import com.trinity.ms.invoicing.sale_session.repository.SaleSessionRepository;
import com.trinity.ms.invoicing.sale_session.service.SaleSessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Service
public class SaleSessionServiceImpl implements SaleSessionService {

    @Autowired
    private SaleSessionRepository saleSessionRepository;

    @Autowired
    @Qualifier("userValidators")
    private List<Validator<Integer>> userValidators;

    @Autowired
    @Qualifier("authorizedUserValidators")
    private List<Validator<Integer>> authorizedUserValidators;

//    @Autowired
//    private ObjectMapper modelMapper;

    private void validateAuthorizedUserConstraint(Integer userId) {
        authorizedUserValidators
            .stream()
                .filter(validator -> !validator.validate(userId))
                .forEach(validator -> {
                    throw new PermissionDeniedException(validator.getMessage());
                });
    }

    private void validateUserSessionConstraint(Integer userId) {
        userValidators
            .stream()
                .filter(validator -> !validator.validate(userId))
                .forEach(validator -> {
                    throw new SessionAlreadyExistsException(validator.getMessage());
                });

    }

    @Override
    public TokenSession open(Authentication authentication, Integer authorizedUser) {

        try {

            ModelMapper modelMapper = new ModelMapper();

            Account account =  modelMapper.map(authentication.getPrincipal(), Account.class);

            Integer currentUser = account.getId();

            validateAuthorizedUserConstraint(authorizedUser);
            validateUserSessionConstraint(currentUser);

            TokenSession tokenSession = TokenSession.generate();

            SaleSession saleSession = new SaleSession();
            saleSession.setCreatedDate(tokenSession.getTimestamp());
            saleSession.setUuid(tokenSession.getToken());
            saleSession.setSaleSessionStatusId(Short.parseShort("1"));
            saleSession.setUserId(currentUser);

            saleSessionRepository.save(saleSession);

            return tokenSession;
        }catch(Exception e){
            return TokenSession.error(e.getMessage());
        }

    }

    @Override
    public boolean close(Principal principal, Integer authorizedUser, String token) {

        try {

            Integer currentUser = 1;

            validateAuthorizedUserConstraint(authorizedUser);
            validateUserSessionConstraint(currentUser);

            TokenSession tokenSession = TokenSession.generate();

            SaleSession saleSession = new SaleSession();
            saleSession.setCreatedDate(tokenSession.getTimestamp());
            saleSession.setUuid(tokenSession.getToken());
            saleSession.setSaleSessionStatusId(Short.parseShort("2"));
            saleSession.setUserId(currentUser);

            saleSessionRepository.save(saleSession);

            return true;
        }catch(Exception e){
            return false;
        }

    }

    @Override
    public boolean standby(Principal principal) {
        return false;
    }

    @Override
    public TokenSession restore(Principal principal) {
        return null;
    }
}
