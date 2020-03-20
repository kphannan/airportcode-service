package com.airline.locationservice.persistence.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;


public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Superman");
        // Use below commented code when will use Spring Security.
    }

    // public User getCurrentAuditor() {

    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      
    //     if (authentication == null || !authentication.isAuthenticated()) {
    //      return null;
    //     }
      
    //     return ((MyUserDetails) authentication.getPrincipal()).getUser();
    // }
}

