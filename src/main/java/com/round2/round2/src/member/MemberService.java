package com.round2.round2.src.member;

import com.round2.round2.config.TokenHelper;
import com.round2.round2.config.exception.CustomException;
import com.round2.round2.config.exception.ErrorCode;
import com.round2.round2.src.domain.Course;
import com.round2.round2.src.domain.Member;
import com.round2.round2.src.member.model.*;
import com.round2.round2.src.post.PostRepository;
import com.round2.round2.utils.JwtService;
import com.round2.round2.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenHelper accessTokenHelper;
    private final TokenHelper refreshTokenHelper;
    private final JwtService jwtService;



    /**
     * 1.1 회원가입
     */
    @Transactional
    public SignupResponse createMember(SignupRequest signupRequest) {
        try {
            String pwd = new SHA256().encrypt(signupRequest.getPwd()); //비밀번호 암호화
            signupRequest.setPwd(pwd);
            Member member = Member.createMember(signupRequest.getName(), signupRequest.getEmail(), signupRequest.getPwd());
            Long id = memberRepository.save(member);
            return new SignupResponse(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 1.2 로그인
     */
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) throws CustomException{
        Member member = memberRepository.findMemberByEmail(loginRequest.getEmail());

        String encryptPwd = new SHA256().encrypt(loginRequest.getPwd());

        if (member == null || !(member.getPwd().equals(encryptPwd))) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Long memberId = member.getId(); //Member 에게 받아온 비밀번호와 방금 암호화한 비밀번호를 비교
        String memberRole = member.getRole();
        TokenHelper.PrivateClaims privateClaims = createPrivateClaims(memberId, memberRole);
        String accessToken = accessTokenHelper.createAccessToken(privateClaims);
        String refreshToken = refreshTokenHelper.createRefreshToken(privateClaims, loginRequest.getEmail());
        LoginResponse loginResponse = new LoginResponse(memberId, accessToken, refreshToken, memberRole);
        return loginResponse;
    }

    /**
     * PrivateClaim 발급
     */

    public TokenHelper.PrivateClaims createPrivateClaims(Long memberId, String memberRole) {
        return new TokenHelper.PrivateClaims(String.valueOf(memberId), memberRole);
    }


    /**
     * 1.3 홈화면 api
     */
    public HomeResponse getHome() {
        Long memberIdByJwt = jwtService.getUserIdx();
        Member member = memberRepository.findMemberById(memberIdByJwt);
//        member.get
        List<MyCurrentCourseDTO> myCurrentCourseDTOList = member.getCourseList().stream()
                .map(c -> new MyCurrentCourseDTO(c))
                .collect(Collectors.toList());

        List<RecommendCourseDTO> recommendCourseDTOList = member.getRecommendList().stream()
                .map(rc -> new RecommendCourseDTO(rc))
                .collect(Collectors.toList());

        HomeResponse homeResponse = new HomeResponse(member.getName(), myCurrentCourseDTOList, recommendCourseDTOList);
        return homeResponse;
    }

    public HomeResponse getHomeForGuest() {
        List<RecommendCourseDTO> topThreeCourseList = memberRepository.getCourseList().stream()
                .map(courseList -> new RecommendCourseDTO(courseList))
                .collect(Collectors.toList());

        HomeResponse homeResponse = new HomeResponse("방문자", topThreeCourseList);
        return homeResponse;
    }
}
