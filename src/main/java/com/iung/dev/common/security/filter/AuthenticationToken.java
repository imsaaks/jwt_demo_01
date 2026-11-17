package com.iung.dev.common.security.filter;

import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationToken extends UsernamePasswordAuthenticationToken {

    public AuthenticationToken(@NotNull Object principal, @NotNull Object credentials) {
        super(principal, credentials);
    }

    public AuthenticationToken(@NotNull Object principal,
                               @NotNull Object credentials,
                               @NotNull Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
