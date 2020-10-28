package com.trinity.ms.invoicing.sale_session.resources;

import com.trinity.commons.payload.Response;
import com.trinity.commons.payload.TokenSession;
import com.trinity.ms.invoicing.sale_session.service.SaleSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/session")
@RestController
public class SaleSessionResource {

    @Autowired
    private SaleSessionService saleSessionService;

    @PostMapping(value = "/open")
    public ResponseEntity<TokenSession> open(Authentication authentication, @RequestParam("supervisor") String supervisorCredentials) {
        return ResponseEntity.ok(saleSessionService.open(authentication, supervisorCredentials));
    }

    @PostMapping(value = "/close")
    public ResponseEntity<Response> close(Authentication authentication, @RequestParam("supervisor") String supervisorCredentials) {
        return ResponseEntity.ok(saleSessionService.close(authentication, supervisorCredentials));
    }

    @PostMapping(value = "/standby")
    public ResponseEntity<Response> standby(Authentication authentication, @RequestParam("supervisor") String supervisorCredentials) {
        return ResponseEntity.ok(saleSessionService.standby(authentication, supervisorCredentials));
    }

    @PostMapping(value = "/restore")
    public ResponseEntity<TokenSession> restore(Authentication authentication, @RequestParam("supervisor") String supervisorCredentials) {
        return ResponseEntity.ok(saleSessionService.restore(authentication, supervisorCredentials));
    }

    @PostMapping(value = "/cancel")
    public ResponseEntity<Response> cancel(Authentication authentication, @RequestParam("supervisor") String supervisorCredentials) {
        return ResponseEntity.ok(saleSessionService.cancel(authentication, supervisorCredentials));
    }

}
