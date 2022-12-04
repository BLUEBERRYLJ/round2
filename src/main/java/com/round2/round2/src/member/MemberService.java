package com.round2.round2.src.member;

import com.round2.round2.config.TokenHelper;
import com.round2.round2.src.domain.Member;
import com.round2.round2.src.member.model.LoginRequest;
import com.round2.round2.src.member.model.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenHelper accessTokenHelper;
    private final TokenHelper refreshTokenHelper;
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) throws Exception{
        Member member = memberRepository.findMemberByEmail(loginRequest.getEmail());
        if (member == null || !(member.getPwd().equals(loginRequest.getPwd()))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long memberId = member.getId(); //Member 에게 받아온 비밀번호와 방금 암호화한 비밀번호를 비교
        String memberRole = member.getRole();
        TokenHelper.PrivateClaims privateClaims = createPrivateClaims(memberId, memberRole);
        String accessToken = accessTokenHelper.createAccessToken(privateClaims);
        String refreshToken = refreshTokenHelper.createRefreshToken(privateClaims, loginRequest.getEmail());
        LoginResponse loginResponse = new LoginResponse(memberId, accessToken, refreshToken);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    /**
     * PrivateClaim 발급
     */

    public TokenHelper.PrivateClaims createPrivateClaims(Long memberId, String memberRole) {
        return new TokenHelper.PrivateClaims(String.valueOf(memberId), memberRole);
    }
}
