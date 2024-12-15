package my.study.springecommercestudy.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import my.study.springecommercestudy.domain.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtGenerator {

    private final long expiration;
    private final SecretKey secretKey;

    public JwtGenerator(@Value("${jwt.secret}") String secretKey
            , @Value("${jwt.expiration}") long expiration) {
        this.expiration = expiration;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generateToken(Member member) {
        return Jwts.builder()
                .claim("id", member.getId())
                .claim("email", member.getEmail())
                .claim("name", member.getName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public Member extractClaims(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(removeBearer(token))
                .getPayload();

        String email = (String) claims.getOrDefault("email", "");
        Long id = (Long) claims.getOrDefault("id", 0L);
        String name = (String) claims.getOrDefault("name", "");

        return new Member(id, email, name);

    }

    public boolean validateToken(String token) {
        return !Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(removeBearer(token))
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    private String removeBearer(String token) {
        return token.replace("Bearer", "").trim();
    }
}