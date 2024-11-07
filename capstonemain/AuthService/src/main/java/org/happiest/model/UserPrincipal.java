package org.happiest.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @param user Additional helper method to get user details if needed
 */

public record UserPrincipal(Users user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converting user role into GrantedAuthority, assuming role is a single string
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Email is used as the username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Can be customized based on the user entity's status
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Can be customized to lock user accounts if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Can be customized to expire credentials after a certain time
    }

    @Override
    public boolean isEnabled() {
        return true; // Can be customized to enable/disable user accounts
    }

}
