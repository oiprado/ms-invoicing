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

import java.security.Principal;

@RequestMapping(value = "/api/session")
@RestController
public class SaleSessionResource {

    @Autowired
    private SaleSessionService saleSessionService;

//    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @PostMapping(value = "/open")
    public ResponseEntity<TokenSession> open(Authentication authentication, @RequestParam("authorize") Integer autorize) {

        return ResponseEntity.ok(saleSessionService.open(authentication, autorize));
    }

//    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @PostMapping(value = "/close")
    public ResponseEntity<Response> close(Principal principal, @RequestParam("authorize") Integer autorize, @RequestParam("token") String token) {
        try{
            saleSessionService.close(principal,autorize, token);
            return ResponseEntity.ok(Response.ok());
        }catch (Exception ex) {
            return ResponseEntity.ok(Response.error(ex.getMessage()));
        }
    }

//    @RequestMapping(value = "/standby", method = RequestMethod.POST)
    @PostMapping(value = "/standby")
    public ResponseEntity<Response> standby(Principal principal, @RequestParam("token") String token) {
        return ResponseEntity.ok(Response.ok());
    }

//    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @PostMapping(value = "/cancel")
    public ResponseEntity<Response> cancel(Principal principal, @RequestParam("token") String token) {
        return ResponseEntity.ok(Response.ok());
    }

//    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    @PostMapping(value = "/restore")
    public ResponseEntity<Response> restore(Principal principal, @RequestParam("userId") Integer userId) {
        return ResponseEntity.ok(Response.ok());
    }

}
