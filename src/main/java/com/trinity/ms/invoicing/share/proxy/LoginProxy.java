package com.trinity.ms.invoicing.share.proxy;

import com.trinity.commons.payload.Token;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-security")
public interface LoginProxy {


    @RequestMapping(method = RequestMethod.POST, value = "/oauth/token")
    @Headers({
        "Authorization: {authorization}"
    })
    Token token(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("grant_type") String grantType
    );
}