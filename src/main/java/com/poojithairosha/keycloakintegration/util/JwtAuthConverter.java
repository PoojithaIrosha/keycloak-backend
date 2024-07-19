package com.poojithairosha.keycloakintegration.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;

    public JwtAuthConverter() {
        this.jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        log.info("START: converting jwt to authentication token");
        final Set<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractUserRoles(jwt).stream()
        ).collect(Collectors.toSet());
        log.info("END: Converted jwt to authentication token");
        return new JwtAuthenticationToken(jwt, authorities);
    }

    private Set<? extends GrantedAuthority> extractUserRoles(Jwt jwt) {
        log.info("Start: Extracting user roles from jwt");
        final Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        final Map<String, Object> veloFleet = (Map<String, Object>) resourceAccess.get("keycloak-integration");
        final List<String> realmRoles = (List<String>) veloFleet.get("roles");

        if (!realmRoles.isEmpty()) {
            Set<SimpleGrantedAuthority> result = realmRoles.stream()
                    .map(role -> {
                        log.info("Authority: {}", role.toUpperCase());
                        return new SimpleGrantedAuthority(role.toUpperCase());
                    })
                    .collect(Collectors.toSet());
            log.info("End: Extracted user roles from jwt");
            return result;
        }

        log.info("End: No user roles found in jwt");
        return Collections.emptySet();
    }
}
