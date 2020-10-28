package com.trinity.ms.invoicing.sale_session.constraints;

import com.trinity.commons.Validator;
import com.trinity.commons.payload.Token;
import com.trinity.ms.invoicing.share.proxy.LoginProxy;
import feign.FeignException;

import java.util.Base64;

public class UserRoleConstraint implements Validator<String> {

    private String message;
    private LoginProxy loginProxy;

    public UserRoleConstraint(LoginProxy loginProxy) {
        this.loginProxy = loginProxy;
    }

    @Override
    public boolean validate(String userCredentials) {
        byte[] decodedBytes;
        String decodedData;
        try{
            decodedBytes = Base64.getUrlDecoder().decode( userCredentials );
            decodedData = new String(decodedBytes);
            String username = decodedData.split(":")[0];
            String password = decodedData.split(":")[1];
            Token token = this.loginProxy.token(
                username,
                password,
                "password"
            );
            return !token.getAccess_token().isEmpty();
        }catch (FeignException.BadRequest ex) {
            message = "Permission denied to supervisor user.";
            return false;
        } catch (IllegalArgumentException exception){
            message = "Supervisor wrong format";
            return false;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
