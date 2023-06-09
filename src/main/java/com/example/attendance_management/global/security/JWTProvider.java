package com.example.attendance_management.global.security;

import com.example.attendance_management.domain.member.domain.Member;
import com.example.attendance_management.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTProvider {

    @Value("${jwt.secret.key}")
    private String salt;

    private Key secretKey;

    private final long exp = 1000L * 60 * 60;

    private final MemberRepository memberRepository;

    private final CustomUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", role);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 정보
                .setIssuedAt(now) // 발급날짜
                .setExpiration(new Date(now.getTime() + exp)) // 만료날짜
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getAccount(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getAccount(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String token) {
        try {
            // bearer 검증
            if (!token.substring(0, "Bearer ".length()).equalsIgnoreCase("Bearer ")) {
                return false;
            }

            token = token.split(" ")[1].trim();
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            // JWT 만료 시  False Return
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception exception) {
            return false;
        }
    }

    public Optional<Member> findMemberByToken(HttpServletRequest httpServletRequest) {
        String bearerToken = resolveToken(httpServletRequest);

        if (!validateToken(bearerToken)) {
            return Optional.empty();
        }

        System.out.println("jafjasdfkdslafjasldljkasjlkfjkl");

        String token = bearerToken.split(" ")[1].trim();
        String username = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return memberRepository.findByUsername(username);
    }

}
