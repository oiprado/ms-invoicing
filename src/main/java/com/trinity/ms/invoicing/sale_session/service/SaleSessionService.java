package com.trinity.ms.invoicing.sale_session.service;

import com.trinity.commons.payload.TokenSession;
import org.springframework.security.core.Authentication;

import java.security.Principal;

public interface SaleSessionService {

    TokenSession open(Authentication authentication, Integer authorizedUser);

    boolean close(Principal principal, Integer authorizedUser, String token);

    boolean standby(Principal principal);

    TokenSession restore(Principal principal);

}
