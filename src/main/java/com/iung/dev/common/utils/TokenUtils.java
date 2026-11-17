package com.iung.dev.common.utils;

import com.iung.dev.common.constants.AppConstants;
import com.iung.dev.domain.user.model.UserDto;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Slf4j
@Component
public class TokenUtils {

    private static final String JWT_KEY="iungDevSecretKeyJWT311XnynbasIWgnWCOESRC6gzpoHMvFvC6d0vMemwwWITheRequirementOf256Bits";
    private static final Key KEY = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
    private static final String JWT_TYPE="JWT";
    private static final String ALGORITHM="HS256";

    public static final String LOGIN_ID="LOGIN_ID";
    public static final String USERNAME="USERNAME";
    public static final String ROLES="ROLES";


    public static String generateToken(UserDto userDto) {
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setHeader(createHeader())
                .setClaims(createClaims(userDto))
                .setSubject(userDto.getMemberId())
                .signWith(KEY, SignatureAlgorithm.HS256)
                .setExpiration(createAccessTokenExpiredDate());

        return builder.compact();
    }

    public static Claims getClaimsFormToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(KEY).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException expiredJwtException) {
            //
            claims = null;
        }
        if (claims == null) {
            throw new NullPointerException();
        }
        return claims;
    }

    /**
     * 토큰을 기반으로 사용자의 정보를 반환해주는 메서드
     */
    public static boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);

            log.debug("expireTime : {}", claims.getExpiration());
            log.debug("loginId : {}", claims.get(LOGIN_ID));
            log.debug("username : {}", claims.get(USERNAME));
            log.debug("roles : {}", claims.get(ROLES));
            // log.debug("permissions : {}", claims.get(PERMISSIONS));
            return true;
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("Token Expired", expiredJwtException);
            return false;
        } catch (JwtException jwtException) {
            log.error("Token Tampered", jwtException);
            return false;
        } catch (NullPointerException npe) {
            log.error("Token is null", npe);
            return false;
        }
    }

    public static String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return Optional.ofNullable(cookies).flatMap(cs -> Arrays.stream(cs).filter(
                        cookie -> cookie.getName().equals(AppConstants.ACCESS_TOKEN))
                .findFirst()
                .map(Cookie::getValue)).orElse(null);
    }
    public static String getUserLoginIdFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return claims.get(LOGIN_ID).toString();
    }

    public static String getUserNameFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return claims.get(USERNAME).toString();
    }
    public static List<String> getAuthoritiesFromToken(String token) {
        return Arrays.asList(getClaimsFormToken(token).get(ROLES).toString().split(","));
    }

    private static Date createAccessTokenExpiredDate() {
        Instant instant = Instant.now();
        Instant expireInstant = instant.plus(Duration.ofHours(AppConstants.TOKEN_EXPIRATION_HOUR));
        return Date.from(expireInstant);
    }
    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", JWT_TYPE);
        header.put("alg", ALGORITHM);
        header.put("regDate", System.currentTimeMillis());
        return header;
    }
    private static Map<String, Object> createClaims(UserDto userDto) {
        Map<String, Object> map = new HashMap<>();
        map.put(LOGIN_ID, userDto.getMemberId());
        map.put(ROLES, "");
        map.put(USERNAME, userDto.getUsername());
        return map;
    }
}
