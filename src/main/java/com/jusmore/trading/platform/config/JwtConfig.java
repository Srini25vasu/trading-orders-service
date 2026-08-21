package com.jusmore.trading.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Configuration
public class JwtConfig {

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return jwtConverter;
    }

    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();
        
        return jwt -> {
            // Get authorities from default converter (scope claims)
            Collection<GrantedAuthority> authorities = defaultConverter.convert(jwt);
            
            // Extract roles from realm_access.roles (Keycloak style)
            List<String> realmRoles = jwt.getClaimAsStringList("realm_access.roles");
            if (realmRoles != null) {
                Collection<GrantedAuthority> realmAuthorities = realmRoles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(toList());
                authorities = Stream.concat(authorities.stream(), realmAuthorities.stream())
                    .toList();
            }
            
            // Extract roles from resource_access (Keycloak client-specific roles)
            // Uncomment if needed:
            // Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
            // if (resourceAccess != null) {
            //     Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.get("your-client-id");
            //     if (clientRoles != null && clientRoles.get("roles") instanceof List) {
            //         List<String> roles = (List<String>) clientRoles.get("roles");
            //         Collection<GrantedAuthority> clientAuthorities = roles.stream()
            //             .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            //             .collect(Collectors.toList());
            //         authorities = Stream.concat(authorities.stream(), clientAuthorities.stream())
            //             .collect(Collectors.toList());
            //     }
            // }
            
            return authorities;
        };
    }
}
