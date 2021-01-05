package com.github.bali.auth.service.impl;

import com.github.bali.auth.constants.EncryptionConstant;
import com.github.bali.auth.service.OAuth2ClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * @author Petty
 */
@Slf4j
@Service
public class BaliClientDetailsServiceImpl implements OAuth2ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails baseClientDetails = new BaseClientDetails("bali-client", "",
                "message.read,message.write", "authorization_code,refresh_token,client_credentials,password,wechat", "",
                "http://localhost:9001/login/oauth2/code/bali");
        baseClientDetails.setClientSecret(EncryptionConstant.SIGNATURE_NOOP + "bali-secret");
        return baseClientDetails;
    }
}
