package org.happiest.service;

import org.happiest.constants.UserDetailsExceptionMessages;
import org.happiest.exception.UserNotFoundException;
import org.happiest.model.UserPrincipal;
import org.happiest.model.Users;
import org.happiest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    // New method to load user by email
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        try {
            // Fetch user by email
            Users user = repo.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException(UserDetailsExceptionMessages.USER_NOT_FOUND_BY_EMAIL + email));

            // Return UserPrincipal for Spring Security
            return new UserPrincipal(user);
        } catch (Exception e) {
            throw new UserNotFoundException(UserDetailsExceptionMessages.USER_LOADING_ERROR + email, e);
        }
    }

    // Spring Security still requires this method, so we just delegate to loadUserByEmail
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return loadUserByEmail(email);  // Delegate to the new method
    }
}
