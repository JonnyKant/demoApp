package com.modeling.demo.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedService {
    public boolean isAuthenticated(Authentication authentication){
        return (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated());
    }
}
