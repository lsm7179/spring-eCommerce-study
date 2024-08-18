package my.study.springecommercestudy.util;

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

    private final String secretKeyValue;
    private final long expiration;
    private final SecretKey secretKey;

    public JwtGenerator(@Value("${jwt.secret}") String secretKey
            , @Value("${jwt.expiration}") long expiration) {
        this.secretKeyValue = secretKey;
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

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKeyValue)
                .build()
                .parseSignedClaims(token)
                .getBody();
    }

}