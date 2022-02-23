package com.github.bali.auth.configuration;

import com.github.bali.auth.filter.BaliClientCredentialsTokenEndpointFilter;
import com.github.bali.auth.provider.error.ResponseExceptionTranslator;
import com.github.bali.auth.provider.granter.WeChatOpenIdTokenGranter;
import com.github.bali.auth.provider.granter.WeChatTokenGranter;
import com.github.bali.auth.provider.granter.WeChatUnionIdTokenGranter;
import com.github.bali.auth.provider.granter.WriteOffTokenGranter;
import com.github.bali.auth.provider.token.BaliAccessTokenConverter;
import com.github.bali.auth.service.OAuth2ClientDetailsService;
import com.github.bali.auth.service.OAuth2UserDetailsService;
import com.github.bali.auth.utils.KeyUtil;
import com.github.bali.security.provider.error.OAuth2AuthExceptionEntryPoint;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.*;

/**
 * @author Petty
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final TokenStore tokenStore;

    private final OAuth2ClientDetailsService clientDetailsService;

    private final OAuth2UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnMissingBean(OAuth2RequestFactory.class)
    public OAuth2RequestFactory requestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setClientDetailsService(clientDetailsService);
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setReuseRefreshToken(false);
        defaultTokenServices.setTokenEnhancer(customerEnhancer());
        return defaultTokenServices;
    }

    public AuthorizationServerConfiguration(TokenStore tokenStore, OAuth2ClientDetailsService clientDetailsService, OAuth2UserDetailsService userDetailsService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.tokenStore = tokenStore;
        this.clientDetailsService = clientDetailsService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        OAuth2AuthExceptionEntryPoint entryPoint = new OAuth2AuthExceptionEntryPoint();
        security.passwordEncoder(passwordEncoder);
        security.authenticationEntryPoint(entryPoint);
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        BaliClientCredentialsTokenEndpointFilter endpoint = new BaliClientCredentialsTokenEndpointFilter(security);
        endpoint.afterPropertiesSet();
        endpoint.setAuthenticationEntryPoint(entryPoint);
        security.addTokenEndpointAuthenticationFilter(endpoint);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenServices(tokenServices());
        endpoints.tokenStore(tokenStore);
        endpoints.authenticationManager(authenticationManager);
        endpoints.approvalStore(approvalStore());
        endpoints.userDetailsService(userDetailsService);
        endpoints.exceptionTranslator(new ResponseExceptionTranslator());
        endpoints.tokenGranter(tokenGranter(endpoints));
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        super.configure(endpoints);
    }

    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        AuthorizationServerTokenServices tokenServices = endpoints.getTokenServices();
        List<TokenGranter> granters = new ArrayList<TokenGranter>(Collections.singletonList(endpoints.getTokenGranter()));
        granters.add(new WeChatTokenGranter(tokenServices, endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), authenticationManager));
        granters.add(new WeChatOpenIdTokenGranter(tokenServices, endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), authenticationManager));
        granters.add(new WeChatUnionIdTokenGranter(tokenServices, endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), authenticationManager));
        granters.add(new WriteOffTokenGranter(tokenServices, endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), authenticationManager));
        return new CompositeTokenGranter(granters);
    }

    @Bean
    public TokenEnhancer customerEnhancer() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(accessTokenConverter()));
        return tokenEnhancerChain;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final RsaSigner signer = new RsaSigner(KeyUtil.getSignerKey());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            private final JsonParser objectMapper = JsonParserFactory.create();

            @Override
            protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String content;
                try {
                    content = this.objectMapper.formatMap(getAccessTokenConverter().convertAccessToken(accessToken, authentication));
                } catch (Exception ex) {
                    throw new IllegalStateException("Cannot convert access token to JSON", ex);
                }
                Map<String, String> headers = new HashMap<>();
                headers.put("kid", KeyUtil.VERIFIER_KEY_ID);
                return JwtHelper.encode(content, signer, headers).getEncoded();
            }
        };
        converter.setSigner(signer);
        converter.setVerifier(new RsaVerifier(KeyUtil.getVerifierKey()));
        converter.setAccessTokenConverter(new BaliAccessTokenConverter());
        return converter;
    }

    @Bean
    public ApprovalStore approvalStore() {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }

    @Bean
    public JWKSet jwkSet() {
        RSAKey.Builder builder = new RSAKey.Builder(KeyUtil.getVerifierKey())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(KeyUtil.VERIFIER_KEY_ID);
        return new JWKSet(builder.build());
    }

}
