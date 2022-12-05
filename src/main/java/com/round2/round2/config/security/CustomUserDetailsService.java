package com.round2.round2.config.security;

import com.round2.round2.config.TokenHelper;
import com.round2.round2.config.handler.JwtHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final JwtHandler jwtHandler;


    @Override
    public CustomUserDetails loadUserByUsername(String parsedToken) throws UsernameNotFoundException {
        return convertTokenToUserDetail(parsedToken);
    }


    /**
     * JWT 에서 Claim 추출 후 UserDetail 반환
     * @param parsedToken
     * @return
     */
    private CustomUserDetails convertTokenToUserDetail(String parsedToken) {
        Optional<TokenHelper.PrivateClaims> privateClaims = jwtHandler.createPrivateClaim(parsedToken);
        return new CustomUserDetails(privateClaims.get().getMemberId(), new SimpleGrantedAuthority(privateClaims.get().getRoleTypes()));
    }
}
