package com.activedgetechnologies.test.config.security;



import com.activedgetechnologies.test.model.user.UserResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication {


    private UserResult userResult;
    private boolean authenticated;

    public UserAuthentication(UserResult userResult) {
        this.userResult = userResult;
        this.authenticated = true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return userResult.getUser().getPassword();
    }

    @Override
    public Object getDetails() {
        return userResult;
    }

    @Override
    public Object getPrincipal() {
        return userResult;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;

    }

    @Override
    public String getName() {
        return userResult.getUser().getEmail();
    }
}
