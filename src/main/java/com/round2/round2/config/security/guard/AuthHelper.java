package com.round2.round2.config.security.guard;

import com.round2.round2.config.security.CustomAuthenticationToken;
import com.round2.round2.config.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
@Slf4j
public class AuthHelper {

    public boolean isAuthenticated() {
        return getAuthentication() instanceof CustomAuthenticationToken && getAuthentication().isAuthenticated();
    }

    public Long extractMemberId() {
        return Long.valueOf(getUserDetails().getUserId());
    }

    public Set<String> extractMemberRoles() {
        return getUserDetails().getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .map(strAuth -> String.valueOf(strAuth))
                .collect(Collectors.toSet());
    }

//    public boolean isAccessTokenType() {
//        return "access".equals(((CustomAuthenticationToken) getAuthentication()).getType());
//    }
//
//    public boolean isRefreshTokenType() {
//        return "refresh".equals(((CustomAuthenticationToken) getAuthentication()).getType());
//    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private CustomUserDetails getUserDetails() {
        return (CustomUserDetails) getAuthentication().getPrincipal();
    }
}
