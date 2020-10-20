package org.ndrshrzg.bikerental.auth;

import org.springframework.security.core.GrantedAuthority;

public class BikeRentalUserAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "USER";
    }
}
