package com.round2.round2.config;


//import com.round2.round2.config.exception.BadRequestException;
import com.round2.round2.config.exception.BadRequestException;
import com.round2.round2.config.handler.JwtHandler;
import com.round2.round2.config.security.CustomAuthenticationToken;
import com.round2.round2.config.security.CustomUserDetails;
import com.round2.round2.config.security.CustomUserDetailsService;
import com.round2.round2.utils.RedisService;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenHelper {

    private final JwtHandler jwtHandler;
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisService redisService;


    @Value("${jwt.max-age.access}") // 1
    private Long accessTokenMaxAgeSeconds;

    @Value("${jwt.max-age.refresh}") // 2
    private Long refreshTokenMaxAgeSeconds;

    @Value("${jwt.key.access}") // 3
    private String accessKey;

    @Value("${jwt.key.refresh}") // 4
    private String refreshKey;

    private static final String ROLE_TYPES = "ROLE_TYPES";
    private static final String MEMBER_ID = "MEMBER_ID";


    public String createAccessToken(PrivateClaims privateClaims) {
        return jwtHandler.createToken(accessKey,
                Map.of(MEMBER_ID, privateClaims.getMemberId(), ROLE_TYPES, privateClaims.getRoleTypes()),
                accessTokenMaxAgeSeconds);
    }


    public String createRefreshToken(PrivateClaims privateClaims, String email) {
        String refreshToken = jwtHandler.createToken(refreshKey,
                Map.of(MEMBER_ID, privateClaims.getMemberId(), ROLE_TYPES, privateClaims.getRoleTypes()),
                refreshTokenMaxAgeSeconds);
        redisService.setValues(email, refreshToken, Duration.ofDays(refreshTokenMaxAgeSeconds));
        return refreshToken;
    }


    public Optional<PrivateClaims> parseRefreshToken(String token, String email) throws BaseException {
        return jwtHandler.checkRefreshToken(refreshKey, token, email).map(claims -> convert(claims));
    }

    private PrivateClaims convert(Claims claims) {
        return new PrivateClaims(claims.get(MEMBER_ID, String.class), claims.get(ROLE_TYPES, String.class));
    }


    /**
     * validate ACCESS Token
     * doFilter ?????? ??????
     */
    public Authentication validateToken(HttpServletRequest request, String token) throws BadRequestException {
        String exception = "exception";

        try {
            Jwts.parser().setSigningKey(accessKey.getBytes()).parseClaimsJws(jwtHandler.untype(token));
            return getAuthentication(token); //loadByUserName ??? Authentication ????????? CustomAuthenticationToken ?????? !!
        } catch (BadRequestException e) {
            request.setAttribute(exception, "????????? ??????????????????. (?????? 'Bearer ' ??????)");
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException e) {
            request.setAttribute(exception, "????????? ???????????????."); //????????? ????????? ???????????????
        } catch (ExpiredJwtException e) {
            request.setAttribute(exception, "????????? ?????????????????????.");
        } catch (IllegalArgumentException e) {
            request.setAttribute(exception, "????????? ??????????????????.");
//        } catch (JwtException e) {
//            e.printStackTrace();
//            request.setAttribute(exception, "????????? ??????????????????."); //??????
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute(exception, "general exception"); //??????
        }
        return null;
    }

    /**
     * loadUserByUsername ?????? UserDetail ??????
     */
    private Authentication getAuthentication(String token) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(token);
        return new CustomAuthenticationToken(userDetails, userDetails.getAuthorities());
    }


    @Getter
    @AllArgsConstructor
    public static class PrivateClaims {
        private String memberId;
        private String roleTypes;
    }
}
