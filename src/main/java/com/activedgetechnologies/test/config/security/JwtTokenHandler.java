package com.activedgetechnologies.test.config.security;


import com.activedgetechnologies.test.exception.InvalidJwtAuthenticationException;
import com.activedgetechnologies.test.model.TokenInfo;
import com.activedgetechnologies.test.model.TokenType;
import com.activedgetechnologies.test.model.user.User;
import com.activedgetechnologies.test.model.user.UserResult;
import com.activedgetechnologies.test.service.UserService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenHandler {

    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${security.jwt.token.secret}")
    private String secretKey;

    @Value("${security.jwt.token.expire.hour}")
    private long securityExpiry;

    private long validityInMilliseconds;

    @Autowired
    private UserService userService;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        secretKey = secretKey.concat(secretKey).concat(secretKey).concat(secretKey).concat(secretKey);
        validityInMilliseconds = securityExpiry * 3600000;
    }

    public String createToken(User user, String role) {

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        if (StringUtils.hasText(role)) {
            claims.put("role", role);
        }

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(validity)
                //.signWith(this.key, SignatureAlgorithm.HS512)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public UserResult getAuthentication(String token) {
        User user = userService.findUserByEmail(getUsername(token));
        if (user != null) {
            user.setLastSeen(new Date());
            userService.createOrUpdateUser(user);
        }
        String userRole = getRole(token);


        return UserResult
                .builder()
                .tokenInfo(
                        TokenInfo.builder()
                                .accessToken(token)
                                .tokenType(TokenType.BEARER)
                                .build()
                )
                .user(user)
                .build();

    }

    private String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    private String getRole(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        return claims.getBody().get("role", String.class);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(HEADER_STRING);
        if (bearerToken != null && bearerToken.toLowerCase().startsWith(TOKEN_PREFIX.toLowerCase())) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) throws Exception {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }


}
