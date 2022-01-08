package com.modeling.demo.domains;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Embeddable;

public enum Role implements GrantedAuthority {
    CLIENT, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
