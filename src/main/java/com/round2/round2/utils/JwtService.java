package com.round2.round2.utils;

import com.round2.round2.config.exception.CustomException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.round2.round2.config.exception.ErrorCode.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class JwtService {

    String type = "Bearer";

    @Value("${jwt.key.access}") // parse 할때
    private String accessKey;

    /**
     Header에서 AUTHORIZATION 으로 JWT 추출
     */
    public String getJwt(){ //resolveToken
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }


    /**
     header에서 받아온 JWT에서 userIdx 추출
     */
    public Long getUserIdx() throws CustomException {

        //1. JWT 추출
        String accessToken = untype(getJwt());
        // 2. userIdx 추출
        return Long.valueOf(
                Jwts.parser()
                        .setSigningKey(accessKey.getBytes())
                        .parseClaimsJws(accessToken)
                        .getBody()
                        .get("MEMBER_ID", String.class));
    }


    private String untype(String token) throws CustomException{
        try {
            return token.substring(type.length());
        } catch (Exception e) {
            throw new CustomException(DATABASE_ERROR);
        }
    }


    public String getUserAuthority() throws CustomException{
        //1. JWT 추출
        String accessToken = untype(getJwt());
        // 2. 권한 추출
        return String.valueOf(
                Jwts.parser()
                        .setSigningKey(accessKey.getBytes())
                        .parseClaimsJws(accessToken)
                        .getBody().get("ROLE_TYPES", String.class));
    }


}
