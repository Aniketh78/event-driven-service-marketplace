package com.booking_micro.bookingService.service;

import com.booking_micro.bookingService.config.UserPrincipal;
import com.booking_micro.bookingService.dto.UserDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IdentityService {

    public UserDetailsDto getAuthDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No user logged in");
        }

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        return new UserDetailsDto(principal.id(), principal.email());

    }
}
