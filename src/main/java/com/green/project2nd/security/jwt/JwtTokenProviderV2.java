package com.green.project2nd.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.project2nd.common.model.AppProperties;
import com.green.project2nd.security.MyUser;
import com.green.project2nd.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;



@Slf4j
@Component
public class JwtTokenProviderV2 {

    private final ObjectMapper om;
    private final AppProperties appProperties;
    private final SecretKey secretKey;

    public JwtTokenProviderV2(ObjectMapper om, AppProperties appProperties) {
        this.om = om;
        this.appProperties = appProperties;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(appProperties.getJwt().getSecret()));
    }


    public String generateAccessToken(MyUser myUser) {
        return generateToken(myUser, appProperties.getJwt().getAccessTokenExpiry());

    }

    public String generateRefreshToken(MyUser myUser) {
        return generateToken(myUser, appProperties.getJwt().getRefreshTokenExpiry());

    }

    private String generateToken(MyUser myUser, long tokenValidMilliSecond) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidMilliSecond))

                .claims(createClaims(myUser))

                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();


    }

    private Claims createClaims(MyUser myUser) {
        try {
            String json = om.writeValueAsString(myUser);
            return Jwts.claims().add("signedUser", json).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UserDetails getUserDetailsFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            String json = (String)claims.get("signedUser");
            MyUser myUser = om.readValue(json, MyUser.class);


            MyUserDetails myUserDetails = new MyUserDetails();
            myUserDetails.setMyUser(myUser);
            return myUserDetails;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }
    }



    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetailsFromToken(token);   // MyUserDetails 객체 주소값
        return userDetails == null ? null : new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }

    public boolean isValidateToken(String token) {
        try {


            return !getClaims(token).getExpiration().before(new Date());


        } catch (Exception e) {
            return false;
        }
    }


    public String resolveToken(HttpServletRequest req) {

        String jwt = req.getHeader(appProperties.getJwt().getHeaderSchemaName());


        if(jwt == null) {
            return null;
        }


        if(!(jwt.startsWith(appProperties.getJwt().getTokenType()))) {        // if(auth.startsWith("Bearer")) -> yaml 파일에서 token-type: Bearer
            return null;                                                      // 프론트와 약속을 만들어야 함.
        }

        // Bearer JWT 문자열 에서 순수한 JWT 문자열만 뽑아내기 위한 문자열 자르기
        return jwt.substring(appProperties.getJwt().getTokenType().length()).trim();   // .trim : 문자열 앞뒤 공백제거 메서드
                                    // (빈칸) + JWT
                                    // appProperties.getJwt().getTokenType() 까지 Bearer 문자열 (Bearer 은 6개 문자 index:0~5)
                                    // Jwt() 객체 주소값
                                    // jwt.substring(6).trim()
//        return jwt.substring(appProperties.getJwt().getTokenType().length() + 1);
//                  .trim() 대신 문자열 + 1로 해서 빈칸을 제외할 수도 있다. but 프론트가 앞뒤로 공백을 보낼 경우 .trim()을 써야 함




    }
}