package com.trinity.ms.invoicing.sale_session.service.impl;

import com.trinity.commons.model.SaleSession;
import com.trinity.commons.payload.Response;
import com.trinity.commons.payload.TokenSession;
import com.trinity.commons.security.payload.Account;
import com.trinity.ms.invoicing.sale_session.constraints.ConstraintComponent;
import com.trinity.ms.invoicing.sale_session.constraints.UserRoleConstraint;
import com.trinity.ms.invoicing.sale_session.repository.SaleSessionRepository;
import com.trinity.ms.invoicing.sale_session.service.SaleSessionService;
import com.trinity.ms.invoicing.sale_session.share.SalesSessionStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SaleSessionServiceImpl implements SaleSessionService {

    private SaleSessionRepository saleSessionRepository;

    private ConstraintComponent constraintComponent;

    private Account retrieveAccountUser(Authentication authentication) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(authentication.getPrincipal(), Account.class);
    }

    private void executeConstraints(Account account, String supervisorCredentials) {
        constraintComponent.validateAuthorizedUserConstraint(supervisorCredentials);
        constraintComponent.validateUserSessionConstraint(account.getId());
    }

    private SaleSession getCurrentSession(Account account, SalesSessionStatus... salesSessionStatus){

        int[] array = Arrays.stream(salesSessionStatus).mapToInt(status -> status.getValue()).toArray();

        List<Short> statuses = new ArrayList();

        for (int index = 0; index < array.length; index++) {
            statuses.add(new Short(String.format("%d", array[index])));
        }

        return saleSessionRepository.getCurrentSession(
            account.getId(),
                statuses
        );
    }

    @Override
    public TokenSession open(Authentication authentication, String supervisorCredentials) {
        try {
            Account account =  retrieveAccountUser(authentication);

            executeConstraints(account, supervisorCredentials);

            constraintComponent.validateByConstraintsClass(supervisorCredentials, UserRoleConstraint.class);

            TokenSession tokenSession = TokenSession.generate();

            SaleSession saleSession = new SaleSession();
            saleSession.setCreatedDate(tokenSession.getTimestamp());
            saleSession.setUuid(tokenSession.getToken());
            saleSession.setSaleSessionStatusId(SalesSessionStatus.OPENED.getValue());
            saleSession.setUserId(account.getId());

            saleSessionRepository.save(saleSession);
            return tokenSession;
        }catch(Exception e){
            return TokenSession.error(e.getMessage());
        }
    }

    @Override
    public Response close(Authentication authentication, String supervisorCredentials) {
        try {
            Account account =  retrieveAccountUser(authentication);

            SaleSession saleSession = getCurrentSession(account, SalesSessionStatus.OPENED);
            saleSession.setSaleSessionStatusId(SalesSessionStatus.CLOSED.getValue());

            saleSessionRepository.save(saleSession);
            return Response.ok();
        }catch(Exception e){
            return Response.error(e.getMessage());
        }
    }

    @Override
    public Response standby(Authentication authentication, String supervisorCredentials) {
        try {
            Account account =  retrieveAccountUser(authentication);

            constraintComponent.validateAuthorizedUserConstraint(supervisorCredentials);

            SaleSession saleSession = getCurrentSession(account, SalesSessionStatus.OPENED);
            saleSession.setSaleSessionStatusId(SalesSessionStatus.STAND_BY.getValue());

            saleSessionRepository.save(saleSession);
            return Response.ok();
        }catch(Exception e){
            return Response.error(e.getMessage());
        }
    }

    @Override
    public TokenSession restore(Authentication authentication, String supervisorCredentials) {
        try {
            Account account =  retrieveAccountUser(authentication);

            constraintComponent.validateAuthorizedUserConstraint(supervisorCredentials);

            SaleSession saleSession = getCurrentSession(account, SalesSessionStatus.STAND_BY);
            saleSession.setSaleSessionStatusId(SalesSessionStatus.OPENED.getValue());

            saleSessionRepository.save(saleSession);

            return TokenSession.ok(saleSession.getUuid());
        }catch(Exception e){
            return TokenSession.error(e.getMessage());
        }
    }

    @Override
    public Response cancel(Authentication authentication, String supervisorCredentials) {
        try {
            Account account =  retrieveAccountUser(authentication);

            constraintComponent.validateAuthorizedUserConstraint(supervisorCredentials);

            SaleSession saleSession = getCurrentSession(account, SalesSessionStatus.OPENED);
            saleSession.setSaleSessionStatusId(SalesSessionStatus.CANCELLED.getValue());

            saleSessionRepository.save(saleSession);

            return Response.ok();
        }catch(Exception e){
            return Response.error(e.getMessage());
        }
    }

    @Autowired
    public void setSaleSessionRepository(SaleSessionRepository saleSessionRepository){
        this.saleSessionRepository = saleSessionRepository;
    }

    @Autowired
    public void setConstraintComponent(ConstraintComponent constraintComponent){
        this.constraintComponent = constraintComponent;
    }

}
