package com.template.bff.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;

@Service
public class InternalApiAccessTokenService {

    private static final String REGISTRATION_ID = "internal-api-client";

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public InternalApiAccessTokenService(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    public String getTokenValue() {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
            .withClientRegistrationId(REGISTRATION_ID)
            .principal(new AnonymousAuthenticationToken(
                "internal-api-client",
                "internal-api-client",
                AuthorityUtils.createAuthorityList("ROLE_SYSTEM")
            ))
            .build();

        OAuth2AuthorizedClient client = authorizedClientManager.authorize(authorizeRequest);
        if (client == null || client.getAccessToken() == null) {
            throw new IllegalStateException("Nao foi possivel obter access token OAuth2 para chamadas internas");
        }

        return client.getAccessToken().getTokenValue();
    }
}

