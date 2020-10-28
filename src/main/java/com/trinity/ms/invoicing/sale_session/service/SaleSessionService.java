package com.trinity.ms.invoicing.sale_session.service;

import com.trinity.commons.model.SaleSession;
import com.trinity.commons.payload.Response;
import com.trinity.commons.payload.TokenSession;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

public interface SaleSessionService {

    TokenSession open(Authentication authentication, String supervisorCredentials);

    Response close(Authentication authentication, String supervisorCredentials);
    Response standby(Authentication authentication, String supervisorCredentials);

    Response cancel(Authentication principal, String supervisorCredentials);

    TokenSession restore(Authentication principal, String supervisorCredentials);

}
