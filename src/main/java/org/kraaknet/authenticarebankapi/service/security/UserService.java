package org.kraaknet.authenticarebankapi.service.security;

import lombok.RequiredArgsConstructor;
import org.kraaknet.authenticarebankapi.controller.exceptions.NotAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * Get the current logged-in user.
     * Wrapper around SecurityContextHolder to a) centralize the logic and b) make it injectable and therefore Mockable
     * without resorting to static mocking
     *
     * @throws NotAuthorizedException if the user is not found in the SecurityContext
     *
     * @return The details of the currently logged in user
     */
    public UserDetails getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(prince -> {
                    if (prince instanceof UserDetails userDetails) {
                        return userDetails;
                    }
                    throw new ClassCastException();
                })
                .orElseThrow(NotAuthorizedException::new);
    }
}
