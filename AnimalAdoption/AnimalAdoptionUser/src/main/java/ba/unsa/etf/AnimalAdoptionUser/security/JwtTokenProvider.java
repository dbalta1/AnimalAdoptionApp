package ba.unsa.etf.AnimalAdoptionUser.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        // Logovanje generisanja tokena
        System.out.println("Generating JWT for: " + email);
        System.out.println("Expiration date: " + expiryDate);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)  // Proveri da je secretKey dobro postavljen
                .compact();
    }
}
